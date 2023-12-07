package app.exception;


import org.springframework.data.util.Pair;

import java.util.List;


/**
 * Выбрасывается при попытке сохранить сущность со значением уникального поля, которое уже присутствует в таблице базы данных.
 */
public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String objectName, List<Pair<String, Object>> objectAttributes) {
        super(
                String.format("%s with %s already exists",
                        objectName,
                        objectAttributes
                )
        );
    }

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
