package app.dto.task;

import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, описывающий запрос на создание задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    /**
     * Имя создаваемой задачи. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     * @see NotNull
     * @see NotBlank
     * @see NotEmpty
     */
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    /**
     * Описание создаваемой задачи. Не может быть {@literal null}, пустым или состоять исключительно из пробелов.
     *@see NotNull
     *@see NotBlank
     *@see NotEmpty
     */
    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    /**
     *Статус создаваемой задачи. Не может быть {@literal null}.
     *@see NotNull
     */
    @NotNull(message = "Status cannot be null")
    private TaskStatus status;

    /**
     * Приоритет создаваемой задачи. Не может быть {@literal null}.
     * @see NotNull
     */
    @NotNull(message = "Priority cannot be null")
    private TaskPriority priority;

    /**
     *Id пользователя, создавшего задачу. Не может быть {@literal null}.
     * @see NotNull
     */
    @NotNull(message = "Creator id cannot be null")
    private UUID creatorId;

    /**
     *Id пользователя, исполняющего задачу. Может быть {@literal null}.
     */
    private UUID executorId;

    /**
     *Дата истечения срока выполнения задачи. Не может быть {@literal null}.
     * @see NotNull
     */
    @NotNull(message = "Expiration date cannot be null")
    private LocalDateTime expiresOn;

    @Override
    public String toString() {
        return "CreateTaskRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", creatorId=" + creatorId +
                ", executorId=" + executorId +
                ", expiresOn=" + expiresOn +
                '}';
    }
}
