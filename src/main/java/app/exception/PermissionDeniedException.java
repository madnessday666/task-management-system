package app.exception;

/**
 * Выбрасывается в случае попытки получения доступа к данным без необходимых привилегий.
 */
public class PermissionDeniedException extends RuntimeException {

    /**
     * Метод создания нового исключения с текстом {@literal message}
     *
     * @param message детали ошибки.
     */
    public PermissionDeniedException(String message) {
        super(message);
    }
}
