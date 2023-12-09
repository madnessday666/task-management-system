package app.repository.task_comment;

import app.entity.task_comment.TaskCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Интерфейс, описывающий методы взаимодействия с базой данных для класса {@link TaskCommentEntity}.
 */
@Repository
public interface TaskCommentRepository extends JpaRepository<TaskCommentEntity, UUID>, PagingAndSortingRepository<TaskCommentEntity, UUID> {

    /**
     * Позволяет выполнять поиск комментариев с указанным {@literal taskId} в базе данных и вернуть результат c заданным количеством страниц.
     *
     * @param taskId   id задачи.
     * @param pageable не должен быть {@literal null}.
     * @return Объект класса {@link Page} со всеми найденными записями из таблицы.
     * @see Pageable
     */
    Page<TaskCommentEntity> findAllByTaskId(UUID taskId, @Nullable Pageable pageable);

    /**
     * Проверка на наличие комментария с указанным {@literal commentId} в базе данных.
     *
     * @param commentId id задачи, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsById(@Nullable UUID commentId);

    /**
     * Проверка на наличие комментария с указанным {@literal commentId} и {@literal userId} в базе данных.
     *
     * @param commentId id задачи, не должен быть {@literal null}.
     * @param userId    id пользователя, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsByIdAndUserId(@Nullable UUID commentId, @Nullable UUID userId);

    @Query(
            value = """
                    DELETE FROM task_comments
                    WHERE id = :commentId
                    RETURNING id
                     """, nativeQuery = true
    )
    UUID deleteTaskCommentById(@Nullable @Param("commentId") UUID commentId);

}
