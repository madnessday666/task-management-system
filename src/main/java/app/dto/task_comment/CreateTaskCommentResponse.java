package app.dto.task_comment;

import app.dto.user.UserDto;
import app.entity.task.TaskEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий ответ на запрос создания комментария.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "id",
        "taskId",
        "content",
        "createdAt",
        "user",
        "timestamp"
})
public class CreateTaskCommentResponse {

    /**
     * Уникальный идентификатор задачи в формате {@link UUID}.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id созданного комментария")
    private UUID id;

    /**
     * Id связанной задачи.
     *
     * @see TaskEntity
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id связанной задачи.")
    private UUID taskId;

    /**
     * Создатель комментария, объект класса {@link UserDto}.
     */
    @Schema(description = "Создатель комментария")
    private UserDto user;

    /**
     * Текст комментария.
     */
    @Schema(example = "This is my comment", description = "Текст комментария")
    private String content;

    /**
     * Дата создания комментария.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата создания комментария")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Дата и время создания ответа на запрос.
     */
    @Builder.Default
    @Schema(description = "Дата и время создания ответа на запрос")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "CreateTaskCommentResponse{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", timestamp=" + timestamp +
                '}';
    }
}
