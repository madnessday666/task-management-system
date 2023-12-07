package app.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс, описывающий ответ на запрос аутентификации пользователя.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * JWT, сгенерированный после успешной аутентификации пользователя.
     */
    @Schema(description = "JWT, сгенерированный после успешной аутентификации пользователя")
    private String jwt;

    /**
     * Дата и время создания ответа на запрос.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата и время создания ответа на запрос")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "jwt='" + jwt + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
