package app.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий запрос на обновление данных пользователя.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    /**
     * Уникальный идентификатор пользователя в формате {@link UUID}. Не может быть {@literal  null}.
     */
    @NotNull(message = "Id cannot be null")
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id обновляемого пользователя")
    private UUID id;

    /**
     * Новое имя пользователя для входа в систему, должно содержать уникальное значение.
     */
    @Pattern(
            regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$",
            message =
                    """        
                            Username must consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
                            Username allowed of the dot (.), underscore (_), and hyphen (-).
                            The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
                            The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., user..name
                            The number of characters must be between 5 to 20.""")
    @Schema(example = "mynewusername", description = "Новое имя пользователя для входа в систему")
    private String username;

    /**
     * Новый пароль пользователя для входа в систему, хранится в зашифрованном виде.
     */
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
            message = """
                    Password must contain 1 number (0-9)
                    Password must contain 1 uppercase letters
                    Password must contain 1 lowercase letters
                    Password must contain 1 non-alpha numeric number
                    Password is 8-16 characters with no space
                     """)
    @Schema(example = "Mynewpass123!", description = "Новый пароль пользователя для входа в систему")
    private String password;

    /**
     * Новое фактическое имя пользователя.
     */
    @Pattern(
            regexp = "^[a-zA-Z\\s].{2,50}+",
            message =
                    """
                            Name can contain only letters a-z,A-Z.
                            Name length must be between 2 to 50.""")
    @Schema(example = "Mynewname", description = "Нвоое фактическое имя пользователя")
    private String name;

    /**
     * Новая электронная почта, должна содержать уникальное значение.
     */
    @Pattern(
            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message =
                    """
                            Email may contain numeric values from 0 to 9.
                            Email may contain letters from a-z, A-Z.
                            Email may contain underscore “_”, hyphen “-“, and dot “.”
                            Email may not contain dots at the start and end of local part.
                            Email may not contain consecutive dots.
                            Email may contain max 64 characters.""")
    @Schema(example = "mynewemail@domain.com", description = "Новая электронная почта пользователя")
    private String email;

    /**
     * Дата и время создания запроса.
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(hidden = true, description = "Дата создания запроса")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
