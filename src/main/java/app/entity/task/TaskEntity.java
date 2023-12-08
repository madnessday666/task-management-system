package app.entity.task;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс, описывающий сущность задачи.
 */
@Entity(name = "tasks")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    /**
     * Уникальный идентификатор задачи в формате {@link UUID}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Имя задачи.
     */
    @Column
    private String name;

    /**
     * Описание зачади.
     */
    @Column
    private String description;

    /**
     * Статус задачи, может принимать одно из допустимых значений {@link TaskStatus}.
     */
    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    /**
     * Приоритет задачи, может принимать одно из допустимых значений {@link TaskPriority}.
     */
    @Column
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    /**
     * Id создателя задачи в формате {@link UUID}.
     */
    @Column
    private UUID creatorId;

    /**
     * Id исполнителя задачи в формате {@link UUID}.
     */
    @Column
    private UUID executorId;

    /**
     * Дата создания задачи, объект класса {@link LocalDateTime}.
     */
    @Column
    private LocalDateTime createdAt;

    /**
     * Дата истечения срока выполнения задачи, объект класса {@link LocalDateTime}.
     */
    @Column
    private LocalDateTime expiresOn;

    /**
     * Дата обнолвения задачи, объект класса {@link LocalDateTime}.
     */
    @Column
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", creatorId=" + creatorId +
                ", executorId=" + executorId +
                ", createdAt=" + createdAt +
                ", expiresOn=" + expiresOn +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TaskEntity task = (TaskEntity) object;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
