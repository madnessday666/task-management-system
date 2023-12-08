package app.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, представляющий пользователя как DTO.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    /**
     * Уникальный идентификатор пользователя в формате {@link UUID}.
     */
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Id пользователя")
    private UUID id;

    /**
     * Имя пользователя для входа в систему.
     */
    @Schema(example = "someusername", description = "Имя пользователя в системе")
    private String username;

    /**
     * Фактическое имя пользователя.
     */
    @Schema(example = "somename", description = "Фактическое имя пользователя")
    private String name;

    /**
     * Электронная почта.
     */
    @Schema(example = "someemail@domain.com", description = "Электронная почта пользователя")
    private String email;

    /**
     * Дата создания пользователя, объект класса {@link LocalDateTime}
     */
    @Schema(example = "2023-12-05T12:40", description = "Дата создания пользователя")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
