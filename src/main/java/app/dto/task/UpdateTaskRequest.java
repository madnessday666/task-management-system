package app.dto.task;

import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий запрос на обновление задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {

    /**
     * Id обновляемой задачи. Не может быть {@literal null}.
     */
    private UUID id;

    /**
     * Новое имя обновляемой задачи.
     */
    private String name;

    /**
     * Новое описание обновляемой задачи.
     */
    private String description;

    /**
     *Новый статус обновляемой задачи.
     */
    private TaskStatus status;

    /**
     * Новый приоритет обновляемой задачи.
     */
    private TaskPriority priority;

    /**
     *Новый Id пользователя, исполняющего задачу.
     */
    private UUID executorId;

    /**
     *Новая дата истечения срока выполнения задачи.
     */
    private LocalDateTime expiresOn;

    @Override
    public String toString() {
        return "UpdateTaskRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", executorId=" + executorId +
                ", expiresOn=" + expiresOn +
                '}';
    }
}
