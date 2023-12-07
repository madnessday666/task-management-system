package app.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий ответ на запрос удаления задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTaskResponse {

    /**
     * Id удаленной задачи.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id удаленной задачи")
    private UUID deletedTaskId;

    /**
     * Дата и время создания ответа на запрос.
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(description = "Дата и время создания ответа на запрос")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "DeleteTaskResponse{" +
                "deletedTaskId=" + deletedTaskId +
                ", timestamp=" + timestamp +
                '}';
    }
}
