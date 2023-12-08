package app.repository.task;

import app.entity.task.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс, описывающий методы взаимодействия с базой данных для класса {@link TaskEntity}.
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID>, JpaSpecificationExecutor<TaskEntity>, PagingAndSortingRepository<TaskEntity, UUID> {


    /**
     * Поиск задачи с указанным {@literal id} в базе данных.
     *
     * @param taskId id задачи.
     * @return {@link Optional}, содержащий объект класса {@link TaskEntity} в случае нахождения.
     */
    Optional<TaskEntity> getTaskEntityById(UUID taskId);

    /**
     * Позволяет выполнять поиск в базе данных по указанным критериям и вернуть результат с заданным количеством страниц.
     * Если {@code Specification} отсутствует - возвращает все записи из БД.
     *
     * @param specification не должен быть {@literal null}.
     * @param pageable      не должен быть {@literal null}.
     * @return Объект класса {@link Page} со всеми найденными записями из таблицы.
     * @see Specification
     * @see Pageable
     */
    @Override
    Page<TaskEntity> findAll(@Nullable Specification<TaskEntity> specification, @Nullable Pageable pageable);

    /**
     * Проверка на наличие задачи с указанным {@literal id} в базе данных.
     *
     * @param id id задачи, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsById(@Nullable UUID id);


    /**
     * Проверка на наличие задачи с указанными {@literal name} и {@literal creatorId} в базе данных.
     *
     * @param name      имя задачи, не должен быть {@literal null}.
     * @param creatorId id создателя задачи, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsByNameAndCreatorId(@Nullable String name, @Nullable UUID creatorId);

    /**
     * Проверка на наличие задачи с указанными {@literal taskId} и {@literal creatorId} в базе данных.
     *
     * @param taskId    id задачи, не должен быть {@literal null}.
     * @param creatorId id создателя задачи, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsByIdAndCreatorId(@Nullable UUID taskId, @Nullable UUID creatorId);

    /**
     * Проверка на наличие задачи с указанными {@literal taskId} и {@literal executorId} в базе данных.
     *
     * @param taskId     id задачи, не должен быть {@literal null}.
     * @param executorId id исполнителя задачи, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsByIdAndExecutorId(@Nullable UUID taskId, @Nullable UUID executorId);

    /**
     * Удаление задачи с указанным {@literal taskId} из базы данных.
     *
     * @param taskId id задачи.
     * @return {@link UUID} - id удаленной задачи.
     */
    @Query(
            value = """
                    DELETE FROM tasks
                    WHERE id = :taskId
                    RETURNING id
                    """, nativeQuery = true)
    UUID deleteTaskById(@Nullable UUID taskId);

}
