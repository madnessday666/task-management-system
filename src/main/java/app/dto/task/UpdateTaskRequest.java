package app.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
     *
     * @see NotNull
     */
    @NotNull(message = "Id cannot be null")
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id обновляемой задачи")
    private UUID id;

    /**
     * Новое имя обновляемой задачи.
     */
    @Schema(example = "New task name", description = "Новое имя задачи")
    private String name;

    /**
     * Новое описание обновляемой задачи.
     */
    @Schema(example = "New task description", description = "Новое описание задачи")
    private String description;

    /**
     * Новый статус обновляемой задачи.
     */
    @Schema(example = "done", allowableValues = {"pending", "in_progress", "done"}, description = "Новый статус задачи")
    private String status;

    /**
     * Новый приоритет обновляемой задачи.
     */
    @Schema(example = "high", allowableValues = {"high", "medium", "low"}, description = "Новый приоритет задачи")
    private String priority;

    /**
     * Новый Id пользователя, исполняющего задачу.
     */
    @Schema(description = "Новый id исполнителя задачи задачи. Может быть null")
    private UUID executorId;

    /**
     * Новая дата истечения срока выполнения задачи.
     */
    @Schema(example = "2023-12-05T12:40", description = "Новая дата истечения срока выполнения задачи")
    private LocalDateTime expiresOn;

    /**
     * Дата и время создания запроса.
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(hidden = true, description = "Дата и время создания запроса")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "UpdateTaskRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", executorId=" + executorId +
                ", expiresOn=" + expiresOn +
                ", timestamp=" + timestamp +
                '}';
    }
}
