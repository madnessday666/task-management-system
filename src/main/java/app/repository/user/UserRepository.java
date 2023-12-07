package app.repository.user;

import app.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс, описывающий методы взаимодействия с базой данных для класса {@link UserEntity}.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, PagingAndSortingRepository<UserEntity, UUID> {

    /**
     * Поиск пользователя с указанным {@literal username} в базе данных.
     *
     * @param username имя пользователя
     * @return {@link Optional}, содержащий объект класса {@link UserEntity} в случае нахождения.
     */
    Optional<UserEntity> getUserEntityByUsername(String username);

    /**
     * Проверка на наличие пользователя с указанным {@literal userId} в базе данных.
     *
     * @param userId id пользователя, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsById(@Nullable UUID userId);

    /**
     * Проверка на наличие пользователя с указанным {@literal username} в базе данных.
     *
     * @param username id пользователя, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsByUsername(@Nullable String username);

    /**
     * Проверка на наличие пользователя с указанным {@literal email} в базе данных.
     *
     * @param email id пользователя, не должен быть {@literal null}.
     * @return {@literal true} - если сущетсвует, в противном случае - {@literal false}
     */
    boolean existsByEmail(@Nullable String email);

    /**
     * Удаление пользователя с указанным {@literal userId} из базы данных.
     *
     * @param userId id пользователя.
     * @return {@link UUID} - id удаленного пользователя.
     */
    @Query(
            value = """
                    DELETE FROM users
                    WHERE id = :userId
                    RETURNING id
                    """, nativeQuery = true)
    UUID deleteUserById(@Param("userId") UUID userId);

}
