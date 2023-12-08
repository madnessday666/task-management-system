package app.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий запрос на создание задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    /**
     * Имя создаваемой задачи. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     *
     * @see NotNull
     * @see NotBlank
     * @see NotEmpty
     */
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(
            regexp = "^[a-zA-Z\\s].{2,100}+",
            message =
                    """
                            Name can contain only letters a-z,A-Z.
                            Name length must be between 2 to 100.""")
    @Schema(example = "Task name", description = "Имя задачи")
    private String name;

    /**
     * Описание создаваемой задачи. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     *
     * @see NotNull
     * @see NotBlank
     * @see NotEmpty
     */
    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @NotEmpty(message = "Description cannot be empty")
    @Schema(example = "Task description", description = "Описание задачи")
    private String description;

    /**
     * Статус создаваемой задачи. Не может быть {@literal null}.
     *
     * @see NotNull
     */
    @NotNull(message = "Status cannot be null")
    @Schema(example = "pending", allowableValues = {"pending", "in_progress", "done"}, description = "Статус задачи")
    private String status;

    /**
     * Приоритет создаваемой задачи. Не может быть {@literal null}.
     *
     * @see NotNull
     */
    @NotNull(message = "Priority cannot be null")
    @Schema(example = "medium", allowableValues = {"high", "medium", "low"}, description = "Приоритет задачи")
    private String priority;

    /**
     * Id пользователя, исполняющего задачу. Может быть {@literal null}.
     */
    @Schema(description = "Id исполнителя задачи задачи")
    private UUID executorId;

    /**
     * Дата и время истечения срока выполнения задачи. Не может быть {@literal null}.
     *
     * @see NotNull
     */
    @NotNull(message = "Expiration date cannot be null")
    @Schema(example = "2023-12-05T12:40", description = "Дата истечения срока выполнения задачи")
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
        return "CreateTaskRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", executorId=" + executorId +
                ", expiresOn=" + expiresOn +
                ", timestamp=" + timestamp +
                '}';
    }
}
