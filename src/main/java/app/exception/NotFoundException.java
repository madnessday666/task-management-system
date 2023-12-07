package app.exception;

import org.springframework.data.util.Pair;

import java.util.List;

/**
 * Выбрасывается в случае когда объект поиска не был найден.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Метод создания нового исключения с параметрами.
     *
     * @param objectName       имя объекта, с которым связано исключение.
     * @param objectAttributes список пар ключ-значение полей объекта, из-за которых произошел выброс исключения.
     */
    public NotFoundException(String objectName, List<Pair<String, Object>> objectAttributes) {
        super(
                String.format("%s with %s not found",
                        objectName,
                        objectAttributes
                )
        );
    }

    /**
     * Метод создания нового исключения с параметрами.
     *
     * @param objectName      имя объекта, с которым связан выброс исключения.
     * @param objectAttribute поле объекта, из-за которого произошел выброс исключения.
     * @param attributeValue  значение поля.
     */
    public NotFoundException(String objectName,
                             String objectAttribute,
                             Object attributeValue) {
        super(
                String.format("%s with %s %s not found",
                        objectName,
                        objectAttribute,
                        attributeValue
                )
        );
    }
}
