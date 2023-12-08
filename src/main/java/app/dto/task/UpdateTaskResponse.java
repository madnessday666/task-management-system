package app.dto.task;

import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id обновленной задачи")
    private UUID id;

    /**
     * Имя обновленной задачи.
     */
    @Schema(example = "Task new name", description = "Имя обновленной задачи")
    private String name;

    /**
     * Описание обновленной задачи.
     */
    @Schema(example = "Task new description", description = "Описание обновленной задачи")
    private String description;

    /**
     * Статус обновленной задачи.
     */
    @Schema(example = "PENDING", description = "Статус обновленной задачи")
    private TaskStatus status;

    /**
     * Приоритет обновленной задачи.
     */
    @Schema(example = "MEDIUM", description = "Приоритет обновленной задачи")
    private TaskPriority priority;

    /**
     * Id пользователя, создавшего задачу.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id создателя обновленной задачи")
    private UUID creatorId;

    /**
     * Id пользователя, выполняющего задачу.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id исполнителя обновленной задачи")
    private UUID executorId;

    /**
     * Дата создания задачи.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата создания задачи")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Дата истечения срока обновленной задачи.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата истечения срока выполнения задачи")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime expiresOn;

    /**
     * Дата обновления задачи.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата обновления задачи")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * Дата и время создания ответа на запрос.
     */
    @Builder.Default
    @Schema(description = "Дата и время создания ответа на запрос")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

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
                ", timestamp=" + timestamp +
                '}';
    }
}
