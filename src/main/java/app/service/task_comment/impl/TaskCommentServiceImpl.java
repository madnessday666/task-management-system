package app.service.task_comment.impl;

import app.dto.task_comment.CreateTaskCommentRequest;
import app.dto.task_comment.CreateTaskCommentResponse;
import app.dto.task_comment.DeleteTaskCommentRequest;
import app.dto.task_comment.DeleteTaskCommentResponse;
import app.entity.task.TaskEntity;
import app.entity.task_comment.TaskCommentEntity;
import app.entity.user.UserEntity;
import app.exception.NotFoundException;
import app.mapper.task.TaskCommentMapper;
import app.repository.task_comment.TaskCommentRepository;
import app.service.task_comment.TaskCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCommentServiceImpl implements TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;

    private final TaskCommentMapper taskCommentMapper;

    /**
     * {@inheritDoc}
     *
     * @see TaskCommentService#getTaskCommentPageByTaskId(UUID, Pageable) (UUID)
     */
    @Override
    public List<TaskCommentEntity> getTaskCommentPageByTaskId(UUID taskId, Pageable pageable) {
        return taskCommentRepository.findAllByTaskId(taskId, pageable).getContent();
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskCommentService#getIsTaskCommentExistsById(UUID)
     */
    @Override
    public boolean getIsTaskCommentExistsById(UUID commentId) {
        return taskCommentRepository.existsById(commentId);
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskCommentService#getIsTaskCommentExistsByIdAndUserId(UUID, UUID)
     */
    @Override
    public boolean getIsTaskCommentExistsByIdAndUserId(UUID commentId, UUID userId) {
        return taskCommentRepository.existsByIdAndUserId(commentId, userId);
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskCommentService#createTaskComment(CreateTaskCommentRequest, TaskEntity, UserEntity)
     */
    @Override
    public CreateTaskCommentResponse createTaskComment(CreateTaskCommentRequest createTaskCommentRequest,
                                                       TaskEntity task,
                                                       UserEntity user) {
        TaskCommentEntity comment = TaskCommentEntity
                .builder()
                .task(task)
                .user(user)
                .content(createTaskCommentRequest.getContent())
                .build();
        taskCommentRepository.saveAndFlush(comment);
        log.info("\nTask comment has been created: {}", comment);
        return taskCommentMapper.toCreateTaskCommentResponse(comment);
    }

    /**
     * {@inheritDoc}
     *
     * @see TaskCommentService#deleteTaskComment(DeleteTaskCommentRequest)
     */
    @Override
    public DeleteTaskCommentResponse deleteTaskComment(DeleteTaskCommentRequest deleteTaskCommentRequest) throws NotFoundException {
        boolean isTaskCommentExists = this.getIsTaskCommentExistsById(deleteTaskCommentRequest.getId());
        if (isTaskCommentExists) {
            UUID deletedTaskCommentId = taskCommentRepository.deleteTaskCommentById(deleteTaskCommentRequest.getId());
            log.info("\nTask comment with id {} has been deleted", deletedTaskCommentId);
            return new DeleteTaskCommentResponse(deletedTaskCommentId, LocalDateTime.now());
        } else {
            throw new NotFoundException("Task comment", "id", deleteTaskCommentRequest.getId());
        }
    }

}
