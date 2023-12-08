package app.dto.auth;

import app.entity.user.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий ответ на запрос регистрации пользователя в системе.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    /**
     * Уникальный идентификатор пользователя в формате {@link UUID}.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id созданного пользователя")
    private UUID id;

    /**
     * Имя пользователя для входа в систему, должно содержать уникальное значение.
     */
    @Schema(example = "myusername", description = "Имя пользователя созданного пользователя")
    private String username;

    /**
     * Пароль пользователя для входа в систему, хранится в зашифрованном виде
     */
    @Builder.Default
    @Schema(example = "[PROTECTED]", description = "Пароль созданного пользователя. Не передается в ответе в целях безопасности")
    private String password = "[PROTECTED]";

    /**
     * Фактическое имя пользователя.
     */
    @Schema(example = "Myname", description = "Фактическое имя созданного пользователя")
    private String name;

    /**
     * Электронная почта, должна содержать уникальное значение.
     */
    @Schema(example = "myemail@domain.com", description = "Электронная почта созданного пользователя")
    private String email;

    /**
     * Роль пользователя в системе, может принимать одно из допустимых значений {@link UserRole}.
     */
    @Schema(example = "ROLE_USER", description = "Роль созданного пользователя")
    private UserRole role;

    /**
     * Дата и время создания пользователя, объект класса {@link LocalDateTime}
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата создания пользователя")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Дата и время обновления пользователя, объект класса {@link LocalDateTime}
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата обновления пользователя")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * Указывает, истек ли срок действия учетной записи пользователя.
     */
    @Schema(example = "false", description = "Указывает, истек ли срок действия учетной записи пользователя")
    private boolean expired;

    /**
     * Указывает, заблокирован ли пользователь.
     */
    @Schema(example = "false", description = "Указывает, заблокирован ли пользователь")
    private boolean locked;

    /**
     * Указывает, истек ли срок действия учетных данных пользователя.
     */
    @Schema(example = "false", description = "Указывает, истек ли срок действия учетных данных пользователя")
    private boolean credentialsExpired;

    /**
     * Указывает, активирован ли пользователь.
     */
    @Schema(example = "true", description = "Указывает, активирован ли пользователь")
    private boolean enabled;

    /**
     * Дата и время создания ответа на запрос.
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата и время создания ответа на запрос")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "RegistrationResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", expired=" + expired +
                ", locked=" + locked +
                ", credentialsExpired=" + credentialsExpired +
                ", enabled=" + enabled +
                ", timestamp=" + timestamp +
                '}';
    }
}
