package app.controller;

import app.dto.error.ApiError;
import app.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Класс для передачи отчета об ошибках в ответе на запрос при их возникновении.
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiError> notFoundHandler(HttpServletRequest httpServletRequest,
                                                    NotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<ApiError> alreadyExistsHandler(HttpServletRequest httpServletRequest,
                                                         AlreadyExistsException exception) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiError> badCredentialsHandler(HttpServletRequest httpServletRequest,
                                                          BadCredentialsException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message("Invalid username or password")
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({LockedException.class})
    public ResponseEntity<ApiError> lockedHandler(HttpServletRequest httpServletRequest,
                                                  LockedException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({CredentialsExpiredException.class})
    public ResponseEntity<ApiError> credentialsExpiredHandler(HttpServletRequest httpServletRequest,
                                                              CredentialsExpiredException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<ApiError> expiredJwtHandler(HttpServletRequest httpServletRequest,
                                                      ExpiredJwtException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String message = String.format(
                "Jwt expired %s. Current date %s",
                exception.getClaims().getExpiration().toString(),
                new Date());
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(message)
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({MalformedJwtException.class})
    public ResponseEntity<ApiError> malformedJwtHandler(HttpServletRequest httpServletRequest,
                                                        MalformedJwtException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message("Incorrect jwt")
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({PageNotFoundException.class})
    public ResponseEntity<ApiError> exceptionHandler(HttpServletRequest httpServletRequest,
                                                     PageNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({AuthorizationHeaderNotPresentException.class})
    public ResponseEntity<ApiError> authorizationHeaderNotPresentHandler(HttpServletRequest httpServletRequest,
                                                                         AuthorizationHeaderNotPresentException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({PermissionDeniedException.class})
    public ResponseEntity<ApiError> permissionDeniedHandler(HttpServletRequest httpServletRequest,
                                                            PermissionDeniedException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> methodArgumentNotValidHandler(HttpServletRequest httpServletRequest,
                                                                  MethodArgumentNotValidException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errorFields = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorFields.add(
                    error.getField() + ":" +
                            Objects.requireNonNull(error.getDefaultMessage()).replaceAll("\n", ","));
        }
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(errorFields.toString())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({InvalidValueException.class})
    public ResponseEntity<ApiError> httpMessageNotReadableHandler(HttpServletRequest httpServletRequest,
                                                                  InvalidValueException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ApiError> usernameNotFoundHandler(HttpServletRequest httpServletRequest,
                                                            UsernameNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Метод для формироваиня отчета об ошибке в виде объекта класса {@link ApiError}.
     *
     * @param httpServletRequest информация о запросе.
     * @param exception          исключение, вознишее в ходе обработки запроса.
     * @return {@link ResponseEntity} с телом {@link ApiError}.
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> exceptionHandler(HttpServletRequest httpServletRequest,
                                                     Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError apiError = ApiError
                .builder()
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();
        return new ResponseEntity<>(apiError, status);
    }

}
