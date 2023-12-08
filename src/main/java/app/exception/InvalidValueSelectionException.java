package app.exception;

/**
 * Выбрасывается когда указанное значение не находится в списке допустимых.
 */
public class InvalidValueSelectionException extends RuntimeException {

    /**
     * Конструктор для создания исключения с заданными параметрами.
     *
     * @param value         указанное значение.
     * @param allowedValues список допустимых значений.
     */
    public InvalidValueSelectionException(String value, Object allowedValues) {
        super(String.format("\"%s\" is not in the list of valid values: [%s]", value, allowedValues));
    }
}
