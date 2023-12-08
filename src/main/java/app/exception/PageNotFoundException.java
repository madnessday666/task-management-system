package app.exception;

/**
 * Выбрасывается при попытке перехода по несуществующему адресу.
 */
public class PageNotFoundException extends RuntimeException {

    /**
     * Конуструктор для создания исключения с заданными параметрами.
     *
     * @param message детали ошибки.
     */
    public PageNotFoundException(String message) {
        super(message);
    }

}
