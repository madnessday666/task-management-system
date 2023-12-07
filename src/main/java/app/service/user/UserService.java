package app.service.user;

import app.dto.auth.RegistrationRequest;
import app.dto.auth.RegistrationResponse;
import app.dto.user.DeleteUserRequest;
import app.dto.user.DeleteUserResponse;
import app.dto.user.UpdateUserRequest;
import app.dto.user.UpdateUserResponse;
import app.entity.user.UserEntity;
import app.exception.AlreadyExistsException;
import app.exception.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс, описывающий методы бизнес-логики для класса {@link UserEntity}
 */
public interface UserService {


    /**
     * Метод, реализующий поиск пользователя с указанным {@literal userId} в базе данных.
     *
     * @param userId id пользователя.
     * @return {@link UserEntity} объект с данными из БД.
     * @throws NotFoundException если пользователь с указанным {@literal userId} не присутствует в БД.
     */
    UserEntity getUserById(UUID userId) throws NotFoundException;

    /**
     * Метод, реализующий поиск пользователя с указанным {@literal username} в базе данных.
     *
     * @param username id username.
     * @return {@link UserEntity} объект с данными из БД.
     * @throws NotFoundException если пользователь с указанным {@literal username} не присутствует в БД.
     */
    UserEntity getUserByUsername(String username) throws NotFoundException;

    /**
     * Позволяет найти всех пользователей с заданным количеством выводимых страниц.
     *
     * @param pageable не должен быть {@literal null}.
     * @return {@link List} со всеми найденными записями из таблицы.
     * @see Pageable
     */
    List<UserEntity> findAll(@Nullable Pageable pageable);

    /**
     * Метод для проверки существования пользователя с указанным {@literal userId} в базе данных.
     *
     * @param userId id пользователя.
     * @return {@literal true} - если пользователь присутствует в БД, в противном случае - {@literal false}.
     */
    boolean getIsUserExistsById(UUID userId);

    /**
     * Метод для проверки существования пользователя с указанным {@literal username} в базе данных.
     *
     * @param username имя пользователя для входа в систему.
     * @return {@literal true} - если пользователь присутствует в БД, в противном случае - {@literal false}.
     */
    boolean getIsUserExistsByUsername(String username);

    /**
     * Метод для проверки существования пользователя с указанным {@literal email} в базе данных.
     *
     * @param email электронная почта пользователя.
     * @return {@literal true} - если пользователь присутствует в БД, в противном случае - {@literal false}.
     */
    boolean getIsUserExistsByEmail(String email);


    /**
     * Метод для создания нового пользователя.
     *
     * @param registrationRequest запрос на создание нового пользователя.
     * @return {@link RegistrationResponse} ответ с данными, возвращенными из БД после сохранения пользователя.
     * @throws AlreadyExistsException если запрос содержит значения уникальных полей (например, {@literal username}), которые уже присутствует в таблице базы данных.
     */
    RegistrationResponse createUser(RegistrationRequest registrationRequest) throws AlreadyExistsException;

    /**
     * Метод для обновления существующего пользователя.
     *
     * @param updateUserRequest запрос на обновление пользователя.
     * @return {@link UpdateUserResponse} ответ с данными, возвращенными из БД после обновления пользователя.
     * @throws AlreadyExistsException если запрос содержит значения уникальных полей (например, {@literal username}), которые уже присутствует в таблице базы данных.
     * @throws NotFoundException      если пользователь с указанным в запросе {@literal id} не присутствует в базе данных.
     */
    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) throws AlreadyExistsException, NotFoundException;

    /**
     * Метод для удаления пользователя.
     *
     * @param deleteUserRequest запрос на удаление пользователя.
     * @return {@link DeleteUserResponse} ответ с данными, возвращенными из БД после удаления пользователя.
     * @throws NotFoundException       если пользователь с указанным в запросе {@literal id} не присутствует в базе данных.
     * @throws BadCredentialsException если указанный в запросе пароль не соответствует значению пароля пользователя в базе данных.
     */
    DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) throws NotFoundException, BadCredentialsException;

}
