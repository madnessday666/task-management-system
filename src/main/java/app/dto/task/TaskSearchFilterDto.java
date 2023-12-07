package app.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс, представляющий собой набор полей для фильтрации значений при отправке запроса.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskSearchFilterDto {

    @Schema(description = "Фильтрация по id задачи")
    private UUID id;

    @Schema(description = "Фильтрация по имени задачи. В случае, если не будет найдено задачи с конкретным именем - будут подобраны наиболее подходящие")
    private String name;

    @Schema(description = "Фильтрация по описанию задачи")
    private String description;

    @Schema(description = "Фильтрация по статусу задачи", allowableValues = {"PENDING", "DONE", "IN_PROGRESS"})
    private String status;

    @Schema(description = "Фильтрация по приоритету задачи", allowableValues = {"HIGH", "MEDIUM", "LOW"})
    private String priority;

    @Schema(description = "Фильтрация по id создателя задачи")
    private UUID creatorId;

    @Schema(description = "Фильтрация по id исполнителя задачи")
    private UUID executorId;

    @Schema(description = "Фильтрация по дате создания задачи, например \"2023-12-5T12:40\"")
    private LocalDateTime createdAt;

    @Schema(description = "Фильтрация по дате создания, значения которой больше или равны указанному, например \"2023-12-5T12:40\"")
    private LocalDateTime createdAtAfter;

    @Schema(description = "Фильтрация по дате создания, значения которой меньше или равны указанному, например \"2023-12-5T12:40\"")
    private LocalDateTime createdAtBefore;

    @Schema(description = "Фильтрация по дате истечения срока выполнения задачи, например \"2023-12-5T12:40\"")
    private LocalDateTime expiresOn;

    @Schema(description = "Фильтрация по дате истечения срока задачи, значения которой больше или равны указанному, например \"2023-12-5T12:40\"")
    private LocalDateTime expiresOnAfter;

    @Schema(description = "Фильтрация по дате истечения срока задачи, значения которой меньше или равны указанному, например \"2023-12-5T12:40\"")
    private LocalDateTime expiresOnBefore;

    @Schema(description = "Фильтрация по дате обновления задачи, например \"2023-12-5T12:40\"")
    private LocalDateTime updatedAt;

    @Schema(description = "Фильтрация по дате обновления задачи, значения которой больше или равны указанному, например \"2023-12-5T12:40\"")
    private LocalDateTime updatedAtAfter;

    @Schema(description = "Фильтрация по дате обновления задачи, значения которой меньше или равны указанному, например \"2023-12-5T12:40\"")
    private LocalDateTime updatedAtBefore;

}
