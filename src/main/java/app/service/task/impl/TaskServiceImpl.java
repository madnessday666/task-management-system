package app.service.task.impl;

import app.dto.task.*;
import app.entity.task.TaskEntity;
import app.exception.AlreadyExistsException;
import app.exception.NotFoundException;
import app.repository.task.TaskRepository;
import app.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс бизнес-логики, реализующий методы {@link TaskService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    /**
     * {@inheritDoc}
     * @see TaskService#getTaskPage(Specification, Pageable)
     */
    @Override
    public Page<TaskEntity> getTaskPage(Specification<TaskEntity> specification, Pageable pageable) {
        return taskRepository.findAll(specification, pageable);
    }


    /**
     * {@inheritDoc}
     * @see TaskService#getTaskById(UUID)
     */
    @Override
    public TaskEntity getTaskById(UUID taskId) throws NotFoundException {
        return taskRepository.getTaskEntityById(taskId).orElseThrow(() -> new NotFoundException("Task", "id", taskId));
    }

    /**
     * {@inheritDoc}
     * @see TaskService#getIsTaskExistsById(UUID)
     */
    @Override
    public boolean getIsTaskExistsById(UUID taskId) {
        return taskRepository.existsById(taskId);
    }

    /**
     * {@inheritDoc}
     * @see TaskService#getIsTaskExistsByNameAndCreatorId(String, UUID)
     */
    @Override
    public boolean getIsTaskExistsByNameAndCreatorId(String name, UUID creatorId) {
        return taskRepository.existsByNameAndCreatorId(name, creatorId);
    }

    /**
     * {@inheritDoc}
     * @see TaskService#createTask(CreateTaskRequest)
     */
    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) throws AlreadyExistsException {
        boolean isTaskWithSpecifiedNameExists =
                this.getIsTaskExistsByNameAndCreatorId(createTaskRequest.getName(), createTaskRequest.getCreatorId());
        if (isTaskWithSpecifiedNameExists) {
            throw new AlreadyExistsException(
                    "Task",
                    List.of(
                            Pair.of("name", createTaskRequest.getName()),
                            Pair.of("creator id", createTaskRequest.getCreatorId())
                    ));
        } else {
            TaskEntity task = taskRepository.saveAndFlush(new TaskEntity(createTaskRequest));
            log.info("\nTask has been created: {}", task);
            return new CreateTaskResponse(task);
        }
    }

    /**
     * {@inheritDoc}
     * @see TaskService#updateTask(UpdateTaskRequest)
     */
    @Override
    public UpdateTaskResponse updateTask(UpdateTaskRequest updateTaskRequest) throws NotFoundException{
        TaskEntity task = this.getTaskById(updateTaskRequest.getId());
        if (this.isStringCorrect(updateTaskRequest.getName())
                && this.areValuesDifferent(task.getName(), updateTaskRequest.getName())) {
            task.setName(updateTaskRequest.getName());
        }
        if (this.isStringCorrect(updateTaskRequest.getDescription())
                && this.areValuesDifferent(task.getDescription(), updateTaskRequest.getDescription())) {
            task.setDescription(updateTaskRequest.getDescription());
        }
        if (updateTaskRequest.getStatus() != null
                && this.areValuesDifferent(task.getStatus(), updateTaskRequest.getStatus())) {
            task.setStatus(updateTaskRequest.getStatus());
        }
        if (updateTaskRequest.getPriority() != null
                && this.areValuesDifferent(task.getPriority(), updateTaskRequest.getPriority())) {
            task.setPriority(updateTaskRequest.getPriority());
        }

        if (this.areValuesDifferent(task.getExecutorId(), updateTaskRequest.getExecutorId())) {
            task.setExecutorId(updateTaskRequest.getExecutorId());
        }

        if (updateTaskRequest.getExpiresOn() != null &&
                this.areValuesDifferent(task.getExpiresOn(), updateTaskRequest.getExpiresOn())) {
            task.setExpiresOn(updateTaskRequest.getExpiresOn());
        }
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.saveAndFlush(task);
        log.info("\nTask has been updated: {}", task);
        return new UpdateTaskResponse(task);
    }

    /**
     * {@inheritDoc}
     * @see TaskService#deleteTask(DeleteTaskRequest)
     */
    @Override
    public DeleteTaskResponse deleteTask(DeleteTaskRequest deleteTaskRequest) throws NotFoundException{
        boolean isTaskExists = this.getIsTaskExistsById(deleteTaskRequest.getId());
        if (isTaskExists) {
            UUID deletedTaskId = taskRepository.deleteTaskById(deleteTaskRequest.getId());
            log.info("\nTask with id {} has been deleted", deletedTaskId);
            return new DeleteTaskResponse(deletedTaskId);
        } else {
            throw new NotFoundException("Task", "id", deleteTaskRequest.getId());
        }
    }

    private boolean isStringCorrect(String value) {
        return value != null && !value.isEmpty() && !value.isBlank();
    }

    private boolean areValuesDifferent(Object firstValue, Object secondValue) {
        return !Objects.equals(firstValue, secondValue);
    }

}
