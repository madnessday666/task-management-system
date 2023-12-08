package app.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс для создания отчета об ошибке, передаваемого в ответе на HTTP запрос.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "status",
        "error",
        "message",
        "path",
        "timestamp"
})
public class ApiError {

    /**
     * Код ответа HTTP.
     *
     * @see org.springframework.http.HttpStatus
     */
    @Schema(defaultValue = "403", description = "Код ответа HTTP")
    private int status;

    /**
     * Имя класса ошибки.
     */
    @Schema(defaultValue = "ExceptionName", description = "Имя (класса) ошибки")
    private String error;

    /**
     * Детали ошибки.
     */
    @Schema(defaultValue = "Exception message", description = "Детали ошибки")
    private String message;

    /**
     * Адрес возникновения ошибки
     */
    @Schema(defaultValue = "/api/v1/path", description = "Адрес возникновения ошибки")
    private String path;

    /**
     * Дата и время возникновения ошибки
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(defaultValue = "2023-12-05T12:40", description = "Дата и время возникновения ошибки")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public String toString() {
        return "ApiError{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
