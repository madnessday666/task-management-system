package app.exception;

import org.springframework.data.util.Pair;

import java.util.List;

/**
 *Выбрасывается в случае когда объект поиска не был найден.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String objectName, List<Pair<String, Object>> objectAttributes) {
        super(
                String.format("%s with %s not found",
                        objectName,
                        objectAttributes
                )
        );
    }

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
