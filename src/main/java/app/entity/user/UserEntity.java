package app.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий сущность пользователя.
 */
@Entity(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    /**
     * Уникальный идентификатор пользователя в формате {@link UUID}.
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Имя пользователя для входа в систему, должно содержать уникальное значение.
     */
    @Column
    private String username;

    /**
     * Пароль пользователя для входа в систему, хранится в зашифрованном виде
     */
    @Column
    private String password;

    /**
     * Фактическое имя пользователя.
     */
    @Column
    private String name;

    /**
     * Электронная почта, должна содержать уникальное значение.
     */
    @Column
    private String email;

    /**
     * Роль пользователя в системе, может принимать одно из допустимых значений {@link UserRole}.
     */
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    /**
     * Дата создания пользователя, объект класса {@link LocalDateTime}
     */
    @Column
    private LocalDateTime createdAt;

    /**
     * Дата обновления пользователя, объект класса {@link LocalDateTime}
     */
    @Column
    private LocalDateTime updatedAt;

}
