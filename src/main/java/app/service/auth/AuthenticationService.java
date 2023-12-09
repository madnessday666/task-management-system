package app.service.auth;

import app.dto.auth.AuthenticationRequest;
import app.dto.auth.AuthenticationResponse;
import app.dto.auth.RegistrationRequest;
import app.dto.auth.RegistrationResponse;
import app.exception.AlreadyExistsException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

/**
 * Интерфейс, описывающий методы аутентификации/регистрации пользователя в системе.
 */
public interface AuthenticationService {

    /**
     * Метод, реализующий аутентификацию пользователя в системе.
     *
     * @param authenticationRequest запрос на аутентификацию.
     * @return {@link AuthenticationResponse} - ответ на запрос в случае успешной аутентификации.
     * @throws DisabledException       если пользователь не активирован.
     * @throws LockedException         если пользователь заблокирован.
     * @throws BadCredentialsException если срок действия учетных данных пользователя истек.
     */
    AuthenticationResponse signIn(AuthenticationRequest authenticationRequest)
            throws DisabledException, LockedException, BadCredentialsException;

    /**
     * Метод, реализующий регистрацию пользователя в системе.
     *
     * @param registrationRequest запрос на регистриацию.
     * @return {@link RegistrationResponse} - ответ на запрос в случае успешной регистрации.
     * @throws AlreadyExistsException если значение уникальных полей запроса (например, {@literal username}) уже имеются в базе данных.
     */
    RegistrationResponse signUp(RegistrationRequest registrationRequest) throws AlreadyExistsException;

}
