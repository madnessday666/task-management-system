package app.dto.task;

import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id созданной задачи")
    private UUID id;

    /**
     * Имя созданной задачи.
     */
    @Schema(example = "Task name", description = "Имя созданной задачи")
    private String name;

    /**
     * Описание созданной задачи.
     */
    @Schema(example = "Task description", description = "Описание созданной задачи")
    private String description;

    /**
     * Статус созданной задачи.
     */
    @Schema(example = "PENDING", description = "Статус созданной задачи")
    private TaskStatus status;

    /**
     * Приоритет созданной задачи.
     */
    @Schema(example = "MEDIUM", description = "Приоритет созданной задачи")
    private TaskPriority priority;

    /**
     * Id пользователя, создавшего задачу.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id создателя задачи")
    private UUID creatorId;

    /**
     * Id пользователя, выполняющего задачу.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id исполнителя задачи")
    private UUID executorId;

    /**
     * Дата создания задачи.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата создания задачи")
    private LocalDateTime createdAt;

    /**
     * Дата истечения срока созданной задачи.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата истечения срока выполнения задачи")
    private LocalDateTime expiresOn;

    /**
     * Дата и время создания ответа на запрос.
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(description = "Дата и время создания ответа на запрос")
    private LocalDateTime timestamp = LocalDateTime.now();

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
                ", timestamp=" + timestamp +
                '}';
    }
}
