package app.dto.task;

import app.entity.task.TaskEntity;
import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий ответ на запрос создания задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskResponse {

    /**
     * Id созданной задачи.
     */
    private UUID id;

    /**
     * Имя созданной задачи.
     */
    private String name;

    /**
     * Описание созданной задачи.
     */
    private String description;

    /**
     * Статус созданной задачи.
     */
    private TaskStatus status;

    /**
     * Приоритет созданной задачи.
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
     * Дата истечения срока созданной задачи.
     */
    private LocalDateTime expiresOn;

    /**
     * Конструктор для создания объекта {@link CreateTaskResponse} из {@link TaskEntity}
     * @param taskEntity созданная задача.
     */
    public CreateTaskResponse(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.name = taskEntity.getName();
        this.description = taskEntity.getDescription();
        this.status = taskEntity.getStatus();
        this.priority = taskEntity.getPriority();
        this.creatorId = taskEntity.getCreatorId();
        this.executorId = taskEntity.getExecutorId();
        this.createdAt = taskEntity.getCreatedAt();
        this.expiresOn = taskEntity.getExpiresOn();
    }

    @Override
    public String toString() {
        return "CreateTaskResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", creatorId=" + creatorId +
                ", executorId=" + executorId +
                ", createdAt=" + createdAt +
                ", expiresOn=" + expiresOn +
                '}';
    }
}
