package app.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * Класс, описывающий запрос на удаление задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTaskRequest {

    /**
     * Id удаляемой задачи. Не может быть {@literal null}.
     * @see NotNull
     */
    @NotNull(message = "Id cannot be null")
    private UUID id;

    @Override
    public String toString() {
        return "DeleteTaskRequest{" +
                "id=" + id +
                '}';
    }

}
