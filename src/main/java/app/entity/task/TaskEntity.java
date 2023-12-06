package app.entity.task;

import app.dto.task.CreateTaskRequest;
import app.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
     * Создатель задачи, объект класса {@link UserEntity}.
     */
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "creator_id")
    @Column
    private UUID creatorId;

    /**
     * Исполнитель задачи, объект класса {@link UserEntity}.
     */
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "executor_id")
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

    /**
     * Конструктор для создания объекта {@link TaskEntity} из {@link CreateTaskRequest}
     * @param createTaskRequest запрос на создание задачи.
     */
    public TaskEntity(CreateTaskRequest createTaskRequest) {
        this.name = createTaskRequest.getName();
        this.description = createTaskRequest.getDescription();
        this.status = createTaskRequest.getStatus();
        this.priority = createTaskRequest.getPriority();
        this.creatorId = createTaskRequest.getCreatorId();
        this.executorId = createTaskRequest.getExecutorId();
        this.createdAt = LocalDateTime.now();
        this.expiresOn = createTaskRequest.getExpiresOn();
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", createdAt=" + createdAt +
                ", expiresOn=" + expiresOn +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
