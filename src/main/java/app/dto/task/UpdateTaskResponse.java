package app.dto.task;

import app.entity.task.TaskEntity;
import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий ответ на запрос обновления задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskResponse {

    /**
     * Id обновленной задачи.
     */
    private UUID id;

    /**
     * Имя обновленной задачи.
     */
    private String name;

    /**
     * Описание обновленной задачи.
     */
    private String description;

    /**
     * Статус обновленной задачи.
     */
    private TaskStatus status;

    /**
     * Приоритет обновленной задачи.
     */
    private TaskPriority priority;

    /**
     * Id пользователя, создавшего задачу.
     */
    private UUID creatorId;

    /**
     * Id пользователя, выполняющего задачу.
     */
    private UUID executorId;

    /**
     * Дата создания задачи.
     */
    private LocalDateTime createdAt;

    /**
     * Дата истечения срока обновленной задачи.
     */
    private LocalDateTime expiresOn;

    /**
     * Дата обновления задачи.
     */
    private LocalDateTime updatedAt;

    public UpdateTaskResponse(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.name = taskEntity.getName();
        this.description = taskEntity.getDescription();
        this.status = taskEntity.getStatus();
        this.priority = taskEntity.getPriority();
        this.creatorId = taskEntity.getCreatorId();
        this.executorId = taskEntity.getExecutorId();
        this.createdAt = taskEntity.getCreatedAt();
        this.expiresOn = taskEntity.getExpiresOn();
        this.updatedAt = taskEntity.getUpdatedAt();
    }

    @Override
    public String toString() {
        return "UpdateTaskResponse{" +
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
}
