package app.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий ответ на запрос удаления пользователя из системы.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserResponse {

    /**
     * Id удаленного пользователя.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id удаленного пользователя")
    private UUID deletedUserId;

    /**
     * Дата и время
     */
    @Builder.Default
    @Schema(example = "2023-12-05T12:40", description = "Дата и время создания ответа на запрос")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "DeleteUserResponse{" +
                "deletedUserId=" + deletedUserId +
                ", timestamp=" + timestamp +
                '}';
    }
}
