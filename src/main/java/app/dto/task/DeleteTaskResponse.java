package app.dto.task;

import lombok.*;

import java.util.UUID;

/**
 * Класс, описывающий ответ на запрос удаления задачи.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTaskResponse {

    private UUID deletedTaskId;

    @Override
    public String toString() {
        return "DeleteTaskResponse{" +
                "deletedTaskId=" + deletedTaskId +
                '}';
    }

}
