package app.exception;

import org.springframework.http.HttpHeaders;

/**
 * Выбрасывается когда в запросе отсутствует {@link HttpHeaders#AUTHORIZATION}
 */
public class AuthorizationHeaderNotPresentException extends RuntimeException {

    public AuthorizationHeaderNotPresentException(String message) {
        super(message);
    }
}
