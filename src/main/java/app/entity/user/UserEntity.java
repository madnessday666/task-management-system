package app.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
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
public class UserEntity implements UserDetails {

    /**
     * Уникальный идентификатор пользователя в формате {@link UUID}.
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
     *
     * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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

    /**
     * Указывает, истек ли срок действия учетной записи пользователя.
     */
    @Column
    private boolean expired = false;

    /**
     * Указывает, заблокирован ли пользователь.
     */
    @Column
    private boolean locked = false;

    /**
     * Указывает, истек ли срок действия учетных данных пользователя.
     */
    @Column
    private boolean credentialsExpired = false;

    /**
     * Указывает, активирован ли пользователь.
     */
    @Column
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + "[PROTECTED]" + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", expired=" + expired +
                ", locked=" + locked +
                ", credentialsExpired=" + credentialsExpired +
                ", enabled=" + enabled +
                '}';
    }
}
