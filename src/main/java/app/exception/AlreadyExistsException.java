package app.exception;


import org.springframework.data.util.Pair;

import java.util.List;


/**
 * Выбрасывается при попытке сохранить сущность со значением уникального поля, которое уже присутствует в таблице базы данных.
 */
public class AlreadyExistsException extends RuntimeException {

    /**
     * Конуструктор для создания исключения с заданными параметрами.
     *
     * @param objectName       имя объекта, связанного с исключением.
     * @param objectAttributes имена и значения полей объекта, связанных с исключением.
     */
    public AlreadyExistsException(String objectName, List<Pair<String, Object>> objectAttributes) {
        super(
                String.format("%s with %s already exists",
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
    public AlreadyExistsException(String objectName,
                                  String objectAttribute,
                                  Object attributeValue) {
        super(
                String.format("%s with %s %s already exists",
                        objectName,
                        objectAttribute,
                        attributeValue
                )
        );
    }

}
