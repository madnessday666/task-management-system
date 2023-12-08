package app.exception;

/**
 * Выбрасывается в случае попытки получения доступа к данным без необходимых привилегий.
 */
public class PermissionDeniedException extends RuntimeException {

    /**
     * Конуструктор для создания исключения с заданными параметрами.
     *
     * @param message детали ошибки.
     */
    public PermissionDeniedException(String message) {
        super(message);
    }
}
