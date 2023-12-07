package app.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий запрос на удаление задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTaskRequest {

    /**
     * Id удаляемой задачи. Не может быть {@literal null}.
     *
     * @see NotNull
     */
    @NotNull(message = "Id cannot be null")
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id удаляемой задачи")
    private UUID id;

    /**
     * Дата и время создания запроса.
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(hidden = true, description = "Дата и время создания запроса")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "DeleteTaskRequest{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }

}
