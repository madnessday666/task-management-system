package app.service.task;

import app.dto.task.*;
import app.entity.task.TaskEntity;
import app.exception.AlreadyExistsException;
import app.exception.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс, описывающий методы бизнес-логики для класса {@link TaskEntity}
 */
public interface TaskService {

    /**
     * Метод, реализующий поиск комментариев по указанным критериям и возвращающий список объектов, определенный
     * номером страницы и количеством элементов.
     *
     * @param specification критерии поиска.
     * @param pageable      размеры возвращаемой страницы данных.
     * @return {@link List} объектов {@link TaskEntity}. Может быть пустым.
     */
    List<TaskEntity> getTaskPage(Specification<TaskEntity> specification, Pageable pageable);

    /**
     * Метод, реализующий поиск задачи с указанным {@literal taskId} в базе данных.
     *
     * @param taskId id задачи.
     * @return {@link TaskEntity} объект с данными из БД.
     * @throws NotFoundException если задача с указанным {@literal taskId} не присутствует в БД.
     */
    TaskEntity getTaskById(UUID taskId) throws NotFoundException;

    /**
     * Метод для проверки на существование задачи с указанным {@literal taskId} в базе данных.
     *
     * @param taskId id задачи.
     * @return {@literal true} - если задача присутствует в БД, в противном случае - {@literal false}.
     */
    boolean getIsTaskExistsById(UUID taskId);

    /**
     * Метод для проверки на существование задачи с указанными {@literal name} и {@literal creatorId} в базе данных.
     *
     * @param name      имя задачи.
     * @param creatorId id создателя задачи.
     * @return {@literal  true} - если задача присутствует в БД, в противном случае - {@literal  false}
     */
    boolean getIsTaskExistsByNameAndCreatorId(String name, UUID creatorId);

    /**
     * Метод для проверки на существование задачи с указанными {@literal taskId} и {@literal creatorId} в базе данных.
     *
     * @param taskId    id задачи.
     * @param creatorId id создателя задачи.
     * @return {@literal  true} - если задача присутствует в БД, в противном случае - {@literal  false}
     */
    boolean getIsTaskExistsByIdAndCreatorId(UUID taskId, UUID creatorId);

    /**
     * Метод для проверки на существование задачи с указанными {@literal taskId} и {@literal executorId} в базе данных.
     *
     * @param taskId     id задачи.
     * @param executorId id исполнителя задачи.
     * @return {@literal  true} - если задача присутствует в БД, в противном случае - {@literal  false}
     */
    boolean getIsTaskExistsByIdAndExecutorId(UUID taskId, UUID executorId);

    /**
     * Метод для создания новой задачи.
     *
     * @param creatorId         id создателя задачи.
     * @param createTaskRequest запрос на создание новой задачи.
     * @return {@link CreateTaskResponse} ответ с данными, возвращенными из БД после сохранения задачи.
     * @throws AlreadyExistsException если задача с указанным в запросе именем уже существует у пользователя,
     *                                указанного в запросе как создатель задачи ({@literal creatorId}).
     */
    CreateTaskResponse createTask(UUID creatorId, CreateTaskRequest createTaskRequest) throws AlreadyExistsException;

    /**
     * Метод для обновления существующей задачи.
     *
     * @param updateTaskRequest запрос на обновление задачи.
     * @return {@link UpdateTaskResponse} ответ с данными, возвращенными из БД после обновления задачи.
     * @throws NotFoundException      если задача с указанным {@literal id} не присутствует в БД.
     * @throws AlreadyExistsException если задача с указанным в запросе именем уже существует у пользователя (creatorId).
     */
    UpdateTaskResponse updateTask(UpdateTaskRequest updateTaskRequest) throws NotFoundException;

    /**
     * Метод для удаления задачи.
     *
     * @param deleteTaskRequest запрос на удаление задачи.
     * @return {@link DeleteTaskResponse} ответ с данными, возвращенными из БД после удаления задачи.
     * @throws NotFoundException если задача с указанным в запросе {@literal id} не присутствует в БД.
     */
    DeleteTaskResponse deleteTask(DeleteTaskRequest deleteTaskRequest) throws NotFoundException;

}
