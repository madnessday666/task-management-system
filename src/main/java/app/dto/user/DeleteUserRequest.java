package app.dto.user;

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
 * Класс, описывающий запрос на удаление пользователя из системы.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserRequest {

    /**
     * Уникальный идентификатор пользователя в формате {@link UUID}. Не может быть {@literal null}.
     *
     * @see NotNull
     * @see NotBlank
     * @see NotEmpty
     */
    @NotNull(message = "Id cannot be null")
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id удаляемого пользователя")
    private UUID id;

    /**
     * Пароль от учетной записи пользователя для подтверждения запроса на удаление. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     *
     * @see NotNull
     * @see NotBlank
     * @see NotEmpty
     */
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
            message = """
                        Password must contain 1 number (0-9)
                        Password must contain 1 uppercase letters
                        Password must contain 1 lowercase letters
                        Password must contain 1 non-alpha numeric number
                        Password is 8-16 characters with no space
                    """)
    @Schema(example = "mypass123", description = "Пароль удаляемого пользователя")
    private String password;

    /**
     * Дата и время создания запроса.
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(hidden = true, description = "Дата и время создания запроса")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "DeleteUserRequest{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
