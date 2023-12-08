package app.service.task_comment;

import app.dto.task.DeleteTaskResponse;
import app.dto.task_comment.CreateTaskCommentRequest;
import app.dto.task_comment.CreateTaskCommentResponse;
import app.dto.task_comment.DeleteTaskCommentRequest;
import app.dto.task_comment.DeleteTaskCommentResponse;
import app.entity.task.TaskEntity;
import app.entity.task_comment.TaskCommentEntity;
import app.entity.user.UserEntity;
import app.exception.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс, описывающий методы бизнес-логики для класса {@link TaskCommentEntity}
 */
public interface TaskCommentService {

    /**
     * Метод, реализующий поиск комментариев по id задачи и возвращающий список объектов, определенный номером страницы и количеством элементов.
     *
     * @param taskId id задачи.
     * @param pageable размеры возвращаемой страницы данных.
     * @return {@link List} объектов {@link TaskCommentEntity}. Может быть пустым.
     */
    List<TaskCommentEntity> getTaskCommentPageByTaskId(UUID taskId, Pageable pageable);

    /**
     * Метод для проверки на существование комментария с указанным {@literal commentId} в базе данных.
     *
     * @param commentId id комментария.
     * @return {@literal true} - если комментарий присутствует в БД, в противном случае - {@literal false}.
     */
    boolean getIsTaskCommentExistsById(UUID commentId);

    /**
     * Метод для проверки на существование комментария с указанными {@literal commentId} и {@literal userId} в базе данных.
     *
     * @param commentId id комментария.
     * @param userId    id пользователя.
     * @return {@literal true} - если комментарий присутствует в БД, в противном случае - {@literal false}.
     */
    boolean getIsTaskCommentExistsByIdAndUserId(UUID commentId, UUID userId);

    /**
     * Метод для создания нового комментария.
     *
     * @param createTaskCommentRequest запрос на создание нового комментария.
     * @return {@link CreateTaskCommentResponse} ответ с данными, возвращенными из БД после сохранения комментария.
     */
    CreateTaskCommentResponse createTaskComment(CreateTaskCommentRequest createTaskCommentRequest,
                                                TaskEntity task,
                                                UserEntity user);

    /**
     * Метод для удаления комментария.
     *
     * @param deleteTaskCommentRequest запрос на удаление комментария.
     * @return {@link DeleteTaskResponse} ответ с данными, возвращенными из БД после удаления комментария.
     * @throws NotFoundException если комментарий с указанным в запросе {@literal id} не присутствует в БД.
     */
    DeleteTaskCommentResponse deleteTaskComment(DeleteTaskCommentRequest deleteTaskCommentRequest) throws NotFoundException;

}
