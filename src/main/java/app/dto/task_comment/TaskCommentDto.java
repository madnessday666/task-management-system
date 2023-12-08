package app.dto.task_comment;

import app.dto.user.UserDto;
import app.entity.task.TaskEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
        "user"
})
public class TaskCommentDto {

    /**
     * Уникальный идентификатор задачи в формате {@link UUID}.
     */
    private UUID id;

    /**
     * Id связанной задачи.
     *
     * @see TaskEntity
     */
    private UUID taskId;

    /**
     * Создатель комментария, объект класса {@link UserDto}.
     */
    private UserDto user;

    /**
     * Текст комментария.
     */
    private String content;

    /**
     * Дата создания комментария.
     */
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String toString() {
        return "TaskCommentDto{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
