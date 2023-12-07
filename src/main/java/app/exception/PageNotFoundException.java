package app.exception;

/**
 * Выбрасывается при попытке перехода по несуществующему адресу.
 */
public class PageNotFoundException extends RuntimeException {

    /**
     * Метод создания нового исключения с текстом {@literal message}
     *
     * @param message детали ошибки.
     */
    public PageNotFoundException(String message) {
        super(message);
    }

}
