package app.repository.task;

import app.dto.task.TaskSearchFilterDto;
import app.entity.task.TaskEntity;
import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Класс для описания фильтров и критериев поиска при обращении к базе данных.
 */
public class TaskSpecification {

    /*
     * Набор имен полей объекта класса TaskEntity
     */

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";
    public static final String PRIORITY = "priority";
    public static final String CREATOR_ID = "creatorId";
    public static final String EXECUTOR_ID = "executorId";
    public static final String CREATED_AT = "createdAt";
    public static final String EXPIRES_ON = "expiresOn";
    public static final String UPDATED_AT = "updatedAt";

    /**
     * Метод, собирающий в себе все объявленные в данном классе методы фильтра данных.
     *
     * @param searchFilter набор полей, по которым будет выполнена фильрация данных.
     * @return {@link Specification} с набором фильтров.
     */
    public static Specification<TaskEntity> filterBy(TaskSearchFilterDto searchFilter) {
        return searchFilter == null ?
                null :
                Specification
                        .where(hasId(searchFilter.getId()))
                        .and(hasName(searchFilter.getName()))
                        .or(likeName(searchFilter.getName()))
                        .and(hasDescription(searchFilter.getDescription()))
                        .and(hasStatus(searchFilter.getStatus()))
                        .and(hasPriority(searchFilter.getPriority()))
                        .and(hasCreatorId(searchFilter.getCreatorId()))
                        .and(hasExecutorId(searchFilter.getExecutorId()))
                        .and(hasCreatedAt(searchFilter.getCreatedAt()))
                        .and(hasCreatedAtAfter(searchFilter.getCreatedAtAfter()))
                        .and(hasCreatedAtBefore(searchFilter.getCreatedAtBefore()))
                        .and(hasExpiresOn(searchFilter.getExpiresOn()))
                        .and(hasExpiresOnAfter(searchFilter.getExpiresOnAfter()))
                        .and(hasExpiresOnBefore(searchFilter.getExpiresOnBefore()))
                        .and(hasUpdatedAt(searchFilter.getUpdatedAt()))
                        .and(hasUpdatedAtAfter(searchFilter.getUpdatedAtAfter()))
                        .and(hasUpdatedAtBefore(searchFilter.getUpdatedAtBefore()));
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному значению {@literal id}.
     */
    private static Specification<TaskEntity> hasId(UUID id) {
        return ((root, query, cb) ->
                id == null ?
                        null :
                        cb.equal(root.get(ID), id));
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному значению {@literal name}.
     */
    private static Specification<TaskEntity> hasName(String name) {
        return ((root, query, cb) ->
                name == null || name.isEmpty() || name.isBlank() ?
                        null :
                        cb.equal(root.get(NAME), name));
    }

    /**
     * Метод, добавляющий фильтрацию по указанному значению {@literal name}. Значение может лишь частично совпадать со значениями полей объектов в БД.
     */
    public static Specification<TaskEntity> likeName(String name) {
        return ((root, query, cb) ->
                name == null || name.isEmpty() || name.isBlank() ?
                        null :
                        cb.like(root.get(NAME), "%" + name + "%"));
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному значению {@literal description}.
     */
    private static Specification<TaskEntity> hasDescription(String description) {
        return ((root, query, cb) ->
                description == null || description.isEmpty() || description.isBlank() ?
                        null :
                        cb.equal(root.get(DESCRIPTION), description));
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному значению {@literal status}.
     */
    private static Specification<TaskEntity> hasStatus(String status) {
        if (status == null) {
            return ((root, query, cb) -> null);
        } else {
            try {
                TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
                return ((root, query, cb) -> cb.equal(root.get(STATUS), taskStatus));
            } catch (IllegalArgumentException e) {
                return ((root, query, cb) -> cb.conjunction());
            }
        }
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному значению {@literal priority}.
     */
    private static Specification<TaskEntity> hasPriority(String priority) {
        if (priority == null) {
            return ((root, query, cb) -> null);
        } else {
            try {
                TaskPriority taskPriority = TaskPriority.valueOf(priority.toUpperCase());
                return ((root, query, cb) -> cb.equal(root.get(PRIORITY), taskPriority));
            } catch (IllegalArgumentException e) {
                return ((root, query, cb) -> cb.conjunction());
            }
        }
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному значению {@literal creatorId}.
     */
    private static Specification<TaskEntity> hasCreatorId(UUID creatorId) {
        return ((root, query, cb) ->
                creatorId == null ?
                        null :
                        cb.equal(root.get(CREATOR_ID), creatorId));
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному значению {@literal executorId}.
     */
    private static Specification<TaskEntity> hasExecutorId(UUID executorId) {
        return ((root, query, cb) ->
                executorId == null ?
                        null :
                        cb.equal(root.get(EXECUTOR_ID), executorId));
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному значению {@literal createdAt}.
     */
    private static Specification<TaskEntity> hasCreatedAt(LocalDateTime createdAt) {
        return ((root, query, cb) ->
                createdAt == null ?
                        null :
                        cb.equal(root.get(CREATED_AT), createdAt));
    }

    /**
     * Метод, добавляющий фильтрацию по полю {@literal createdAt}, где все значения больше или равны {@literal createdAtAfter}.
     */
    private static Specification<TaskEntity> hasCreatedAtAfter(LocalDateTime createdAtAfter) {
        return ((root, query, cb) ->
                createdAtAfter == null ?
                        null :
                        cb.greaterThanOrEqualTo(root.get(CREATED_AT), createdAtAfter));
    }

    /**
     * Метод, добавляющий фильтрацию по полю {@literal createdAt}, где все значения меньше или равны {@literal createdAtBefore}.
     */
    private static Specification<TaskEntity> hasCreatedAtBefore(LocalDateTime createdAtBefore) {
        return ((root, query, cb) ->
                createdAtBefore == null ?
                        null :
                        cb.lessThanOrEqualTo(root.get(CREATED_AT), createdAtBefore));
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному полю {@literal expiresOn}.
     */
    private static Specification<TaskEntity> hasExpiresOn(LocalDateTime expiresOn) {
        return ((root, query, cb) ->
                expiresOn == null ?
                        null :
                        cb.equal(root.get(EXPIRES_ON), expiresOn));
    }

    /**
     * Метод, добавляющий фильтрацию по полю {@literal expiresOn}, где все значения больше или равны {@literal expiresOnAfter}.
     */
    private static Specification<TaskEntity> hasExpiresOnAfter(LocalDateTime expiresOnAfter) {
        return ((root, query, cb) ->
                expiresOnAfter == null ?
                        null :
                        cb.greaterThanOrEqualTo(root.get(EXPIRES_ON), expiresOnAfter));
    }

    /**
     * Метод, добавляющий фильтрацию по полю {@literal expiresOn}, где все значения меньше или равны {@literal expiresOnBefore}.
     */
    private static Specification<TaskEntity> hasExpiresOnBefore(LocalDateTime expiresOnBefore) {
        return ((root, query, cb) ->
                expiresOnBefore == null ?
                        null :
                        cb.lessThanOrEqualTo(root.get(EXPIRES_ON), expiresOnBefore));
    }

    /**
     * Метод, добавляющий фильтрацию строго по указанному полю {@literal updatedAt}.
     */
    private static Specification<TaskEntity> hasUpdatedAt(LocalDateTime updatedAt) {
        return ((root, query, cb) ->
                updatedAt == null ?
                        null :
                        cb.equal(root.get(UPDATED_AT), updatedAt));
    }

    /**
     * Метод, добавляющий фильтрацию по полю {@literal updatedAt}, где все значения больше или равны {@literal updatedAtAfter}.
     */
    private static Specification<TaskEntity> hasUpdatedAtAfter(LocalDateTime updatedAtAfter) {
        return ((root, query, cb) ->
                updatedAtAfter == null ?
                        null :
                        cb.greaterThanOrEqualTo(root.get(UPDATED_AT), updatedAtAfter));
    }

    /**
     * Метод, добавляющий фильтрацию по полю {@literal updatedAt}, где все значения меньше или равны {@literal updatedAtBefore}.
     */
    private static Specification<TaskEntity> hasUpdatedAtBefore(LocalDateTime updatedAtBefore) {
        return ((root, query, cb) ->
                updatedAtBefore == null ?
                        null :
                        cb.lessThanOrEqualTo(root.get(UPDATED_AT), updatedAtBefore));
    }


}
