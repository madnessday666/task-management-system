package app.dto.task_comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс, описывающий запрос на создание комментария.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskCommentRequest {

    /**
     * Текст комментария. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     *
     * @see NotNull
     * @see NotBlank
     * @see NotEmpty
     */
    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    @NotEmpty(message = "Content cannot be empty")
    @Schema(example = "This is my comment", description = "Текст комментария")
    private String content;

    /**
     * Дата и время создания запроса.
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(hidden = true, description = "Дата и время создания запроса")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "CreateTaskCommentRequest{" +
                "content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
