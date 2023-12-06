package app.service.task;

import app.dto.task.*;
import app.entity.task.TaskEntity;
import app.exception.AlreadyExistsException;
import app.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * Интерфейс, описывающий методы бизнес-логики для класса {@link TaskEntity}
 */
public interface TaskService {

    /**
     * Метод, реализующий поиск задач по указанным критериям в базе данных.
     *
     * @param specification критерии поиска.
     * @param pageable      размеры возвращаемой страницы данных.
     * @return {@link Page} со всеми найденными в БД задачами.
     */
    Page<TaskEntity> getTaskPage(Specification<TaskEntity> specification, Pageable pageable);

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
     * @return {@literal true} - если задача присутствует в БД, в противном случае - {@literal false}
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
     * Метод для создания новой задачи с последующим сохранением ее в базе данных.
     * @param createTaskRequest запрос для создания новой задачи.
     * @return {@link CreateTaskResponse} ответ с данными, возвращенными из БД после сохранения задачи.
     * @throws AlreadyExistsException если задача с указанным в запросе именем уже существует у пользователя,
     * указанного в запросе как создатель задачи ({@literal creatorId}).
     */
    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) throws AlreadyExistsException;

    /**
     * Метод для обновления существующей задачи.
     * @param updateTaskRequest запрос для обновления задачи.
     * @return {@link UpdateTaskResponse} ответ с данными, возвращенными из БД после обновления задачи.
     * @throws NotFoundException если задача с указанным {@literal id} не присутствует в БД.
     */
    UpdateTaskResponse updateTask(UpdateTaskRequest updateTaskRequest) throws NotFoundException;

    /**
     * Метод для удаления задачи.
     * @param deleteTaskRequest запрос для удаления задачи.
     * @return {@link DeleteTaskResponse} ответ с данными, возвращенными из БД после удаления задачи.
     * @throws NotFoundException если задача с указанным {@literal id} не присутствует в БД.
     */
    DeleteTaskResponse deleteTask(DeleteTaskRequest deleteTaskRequest) throws NotFoundException;

}
