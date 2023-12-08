package app.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс, описывающий запрос на аутентификацию пользователя в системе.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * Имя пользователя. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     */
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @NotEmpty(message = "Username cannot be empty")
    @Schema(example = "myusername", description = "Имя пользователя для входа в систему")
    private String username;

    /**
     * Пароль пользователя. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     */
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @NotEmpty(message = "Password cannot be empty")
    @Schema(example = "Mypass123!", description = "Пароль пользователя для входа в систему")
    private String password;

    /**
     * Дата и время создания запроса.
     */
    @Schema(hidden = true, description = "Дата и время создания запроса")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
