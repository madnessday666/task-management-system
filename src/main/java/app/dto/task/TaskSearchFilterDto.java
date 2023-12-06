package app.dto.task;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskSearchFilterDto {

    private UUID id;

    private String name;

    private String description;

    private String status;

    private String priority;

    private UUID creatorId;

    private UUID executorId;

    private LocalDateTime createdAt;

    private LocalDateTime createdAtAfter;

    private LocalDateTime createdAtBefore;

    private LocalDateTime expiresOn;

    private LocalDateTime expiresOnAfter;

    private LocalDateTime expiresOnBefore;

    private LocalDateTime updatedAt;

    private LocalDateTime updatedAtAfter;

    private LocalDateTime updatedAtBefore;

}
