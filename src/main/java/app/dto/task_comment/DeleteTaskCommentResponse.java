package app.dto.task_comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий ответ на запрос удаления комментария.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTaskCommentResponse {

    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id удаленного комментария")
    private UUID deletedCommentId;

    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(hidden = true, description = "Дата и время создания запроса")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "DeleteTaskCommentResponse{" +
                "deletedCommentId=" + deletedCommentId +
                ", timestamp=" + timestamp +
                '}';
    }
}
