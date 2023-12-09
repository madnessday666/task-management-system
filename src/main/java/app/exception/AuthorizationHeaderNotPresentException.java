package app.exception;

import org.springframework.http.HttpHeaders;

/**
 * Выбрасывается когда в запросе отсутствует {@link HttpHeaders#AUTHORIZATION}
 */
public class AuthorizationHeaderNotPresentException extends RuntimeException {

    /**
     * Конструктор для создания исключения.
     */
    public AuthorizationHeaderNotPresentException() {
        super("Authorization header not present");
    }

}
