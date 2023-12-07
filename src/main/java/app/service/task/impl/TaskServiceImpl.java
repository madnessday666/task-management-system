package app.service.task.impl;

import app.dto.task.*;
import app.entity.task.TaskEntity;
import app.exception.AlreadyExistsException;
import app.exception.NotFoundException;
import app.mapper.task.TaskMapper;
import app.repository.task.TaskRepository;
import app.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Класс бизнес-логики, реализующий методы {@link TaskService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    /**
     * {@inheritDoc}
     *
     * @see TaskService#getTaskPage(Specification, Pageable)
     */
    @Override
    public List<TaskEntity> getTaskPage(Specification<TaskEntity> specification, Pageable pageable) {
        return taskRepository.findAll(specification, pageable).getContent();
    }


    /**
     * {@inheritDoc}
     *
     * @see TaskService#getTaskById(UUID)
     */
    @Override
    public TaskEntity getTaskById(UUID taskId) throws NotFoundException {
        return taskRepository.getTaskEntityById(taskId).orElseThrow(() -> new NotFoundException("Task", "id", taskId));
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskService#getIsTaskExistsById(UUID)
     */
    @Override
    public boolean getIsTaskExistsById(UUID taskId) {
        return taskRepository.existsById(taskId);
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskService#getIsTaskExistsByNameAndCreatorId(String, UUID)
     */
    @Override
    public boolean getIsTaskExistsByNameAndCreatorId(String name, UUID creatorId) {
        return taskRepository.existsByNameAndCreatorId(name, creatorId);
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskService#getIsTaskExistsByIdAndCreatorId(UUID, UUID)
     */
    @Override
    public boolean getIsTaskExistsByIdAndCreatorId(UUID taskId, UUID creatorId) {
        return taskRepository.existsByIdAndCreatorId(taskId, creatorId);
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskService#getIsTaskExistsByIdAndExecutorId(UUID, UUID)
     */
    @Override
    public boolean getIsTaskExistsByIdAndExecutorId(UUID taskId, UUID executorId) {
        return taskRepository.existsByIdAndExecutorId(taskId, executorId);
    }

    /**
     * {@inheritDoc}
     *
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
            TaskEntity taskEntity = taskMapper.toTaskEntity(createTaskRequest);
            taskEntity.setCreatedAt(LocalDateTime.now());
            TaskEntity createdTask = taskRepository.saveAndFlush(taskEntity);
            log.info("\nTask has been created: {}", createdTask);
            return taskMapper.toCreateTaskResponse(createdTask);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskService#updateTask(UpdateTaskRequest)
     */
    @Override
    public UpdateTaskResponse updateTask(UpdateTaskRequest updateTaskRequest) throws NotFoundException {
        TaskEntity task = this.getTaskById(updateTaskRequest.getId());
        taskMapper.toTaskEntity(updateTaskRequest, task);
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.saveAndFlush(task);
        log.info("\nTask has been updated: {}", task);
        return taskMapper.toUpdateTaskResponse(task);
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskService#deleteTask(DeleteTaskRequest)
     */
    @Override
    public DeleteTaskResponse deleteTask(DeleteTaskRequest deleteTaskRequest) throws NotFoundException {
        boolean isTaskExists = this.getIsTaskExistsById(deleteTaskRequest.getId());
        if (isTaskExists) {
            UUID deletedTaskId = taskRepository.deleteTaskById(deleteTaskRequest.getId());
            log.info("\nTask with id {} has been deleted", deletedTaskId);
            return new DeleteTaskResponse(deletedTaskId, LocalDateTime.now());
        } else {
            throw new NotFoundException("Task", "id", deleteTaskRequest.getId());
        }
    }

}
