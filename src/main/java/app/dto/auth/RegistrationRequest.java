package app.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс, описывающий запрос на регистрацию пользователя в системе.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    /**
     * Имя пользователя для входа в систему, должно содержать уникальное значение. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     */
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @NotEmpty(message = "Username cannot be empty")
    @Pattern(
            regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$",
            message =
                    """        
                            Username can contain only alphanumeric characters (a-zA-Z0-9), dots(.), underscores(_) and hyphen (-).
                            The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
                            The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., user..name
                            Username length must be between 5 to 20.""")
    @Schema(example = "myusername", description = "Имя пользователя для входа в систему")
    private String username;

    /**
     * Пароль пользователя для входа в систему, хранится в зашифрованном виде. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
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
    @Schema(example = "Mypass123!", description = "Пароль пользователя для входа в систему")
    private String password;

    /**
     * Фактическое имя пользователя. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     */
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(
            regexp = "^[a-zA-Z\\s].{2,50}+",
            message =
                    """
                            Name can contain only letters a-z,A-Z.
                            Name length must be between 2 to 50.""")
    @Schema(example = "Myname", description = "Фактическое имя пользователя")
    private String name;

    /**
     * Электронная почта, должна содержать уникальное значение. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     */
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @NotEmpty(message = "Email cannot be empty")
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
    @Schema(example = "myemail@domain.com", description = "Электронная почта пользователя")
    private String email;

    /**
     * Дата и время создания запроса.
     */
    @Schema(hidden = true, description = "Дата и время создания запроса")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
