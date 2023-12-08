package app.entity.task_comment;

import app.entity.task.TaskEntity;
import app.entity.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс, описывающий сущность комментария к задаче.
 */
@Entity(name = "task_comments")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCommentEntity {

    /**
     * Уникальный идентификатор задачи в формате {@link UUID}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Id связанной задачи.
     *
     * @see TaskEntity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private TaskEntity task;

    /**
     * Создатель комментария, объект класса {@link UserEntity}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserEntity user;

    /**
     * Текст комментария.
     */
    @Column
    private String content;

    /**
     * Дата создания комментария.
     */
    @Column
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String toString() {
        return "TaskCommentEntity{" +
                "id=" + id +
                ", task=" + task.getId() +
                ", user=" + user.getId() +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TaskCommentEntity comment = (TaskCommentEntity) object;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
