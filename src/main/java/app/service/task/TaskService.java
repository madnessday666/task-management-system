package app.service.task;

import app.dto.task.*;
import app.entity.task.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * Интерфейс, описывающий методы бизнес-логики для класса {@link TaskEntity}
 */
public interface TaskService {

    Page<TaskEntity> getTaskPage(Specification<TaskEntity> specification, Pageable pageable);

    TaskEntity getTaskById(UUID taskId);

    boolean getIsTaskExistsById(UUID taskId);

    boolean getIsTaskExistsByNameAndCreatorId(String name, UUID creatorId);

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    UpdateTaskResponse updateTask(UpdateTaskRequest updateTaskRequest);

    DeleteTaskResponse deleteTask(DeleteTaskRequest deleteTaskRequest);

}
