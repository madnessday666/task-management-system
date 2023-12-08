package app.dto.task;

import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, представляющий задачу как DTO.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    /**
     * Уникальный идентификатор задачи в формате {@link UUID}.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id задачи")
    private UUID id;

    /**
     * Имя задачи.
     */
    @Schema(example = "Task name", description = "Имя задачи")
    private String name;

    /**
     * Описание зачади.
     */
    @Schema(example = "Task description", description = "Описание задачи")
    private String description;

    /**
     * Статус задачи, может принимать одно из допустимых значений {@link TaskStatus}.
     */
    @Schema(example = "PENDING", description = "Статус задачи")
    private TaskStatus status;

    /**
     * Приоритет задачи, может принимать одно из допустимых значений {@link TaskPriority}.
     */
    @Schema(example = "MEDIUM", description = "Приоритет задачи")
    private TaskPriority priority;

    /**
     * Id создателя задачи в формате {@link UUID}.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id создателя задачи")
    private UUID creatorId;

    /**
     * Id исполнителя задачи в формате {@link UUID}.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id исполнителя задачи")
    private UUID executorId;

    /**
     * Дата создания задачи, объект класса {@link LocalDateTime}.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата создания задачи")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Дата истечения срока выполнения задачи, объект класса {@link LocalDateTime}.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата истечения срока выполнения задачи")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime expiresOn;

    /**
     * Дата обнолвения задачи, объект класса {@link LocalDateTime}.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата обновления задачи")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "TaskDto{" +
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
