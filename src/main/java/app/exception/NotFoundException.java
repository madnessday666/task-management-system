package app.exception;

import org.springframework.data.util.Pair;

import java.util.List;

/**
 * Выбрасывается в случае когда объект поиска не был найден.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Конуструктор для создания исключения с заданными параметрами.
     *
     * @param objectName       имя объекта, связанного с исключением.
     * @param objectAttributes имена и значения полей объекта, связанных с исключением.
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
     * Конуструктор для создания исключения с заданными параметрами.
     *
     * @param objectName      имя объекта, связанного с исключением.
     * @param objectAttribute имя  поляобъекта, связанное с исключением.
     * @param attributeValue  значения поля объекта, связанное с исключением.
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
