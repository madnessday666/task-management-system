package app.mapper.task;

import app.dto.task.*;
import app.entity.task.TaskEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для выполнения различных преобразований над объектом {@link TaskEntity}.
 */
@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final ModelMapper modelMapper;

    /**
     * Инициализация типов преобразований.
     */
    @PostConstruct
    private void init() {
        modelMapper
                .createTypeMap(UpdateTaskRequest.class, TaskEntity.class)
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateTaskRequest::getName, TaskEntity::setName))
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateTaskRequest::getDescription, TaskEntity::setDescription))
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateTaskRequest::getStatus, TaskEntity::setStatus))
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateTaskRequest::getPriority, TaskEntity::setPriority))
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateTaskRequest::getExecutorId, TaskEntity::setExecutorId))
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateTaskRequest::getExpiresOn, TaskEntity::setExpiresOn));
        modelMapper.createTypeMap(TaskEntity.class, CreateTaskResponse.class);
        modelMapper.createTypeMap(TaskEntity.class, UpdateTaskResponse.class);
        modelMapper.createTypeMap(CreateTaskRequest.class, TaskEntity.class);
        modelMapper.createTypeMap(TaskEntity.class, TaskDto.class);
    }

    /**
     * Метод преобразования {@link TaskEntity} в {@link TaskDto}.
     *
     * @param taskEntity источник, объект класса {@link TaskEntity}.
     * @return {@link TaskDto} со значениями полей от переданного {@link TaskEntity}.
     */
    public TaskDto toDto(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDto.class);
    }

    /**
     * Метод преобразования {@link List} объектов {@link TaskEntity} в {@link List} объектов {@link TaskDto}.
     *
     * @param taskEntities источник, список объектов класса {@link TaskEntity}.
     * @return {@link List} объектов {@link TaskDto} со значениями полей от переданных {@link TaskEntity}.
     */
    public List<TaskDto> toDtoList(List<TaskEntity> taskEntities) {
        return taskEntities
                .stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Метод преобразования {@link TaskEntity} в {@link CreateTaskResponse}.
     *
     * @param taskEntity источник, объект класса {@link TaskEntity}.
     * @return {@link CreateTaskResponse} со значениями полей от переданного {@link TaskEntity}.
     */
    public CreateTaskResponse toCreateTaskResponse(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, CreateTaskResponse.class);
    }

    /**
     * Метод преобразования {@link TaskEntity} в {@link UpdateTaskResponse}.
     *
     * @param taskEntity источник, объект класса {@link TaskEntity}.
     * @return {@link UpdateTaskResponse} со значениями полей от переданного {@link TaskEntity}.
     */
    public UpdateTaskResponse toUpdateTaskResponse(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, UpdateTaskResponse.class);
    }

    /**
     * Метод преобразования {@link CreateTaskRequest} в {@link TaskEntity}.
     *
     * @param createTaskRequest источник, объект класса {@link CreateTaskRequest}.
     * @return {@link TaskEntity} со значениями полей от переданного {@link CreateTaskRequest}.
     */
    public TaskEntity toTaskEntity(CreateTaskRequest createTaskRequest) {
        return modelMapper.map(createTaskRequest, TaskEntity.class);
    }

    /**
     * Метод передачи значения полей {@link UpdateTaskResponse} объекту {@link TaskEntity}. Поля со значением {@literal null} будут пропущены.
     *
     * @param updateTaskRequest источник, объект класса {@link UpdateTaskResponse}
     * @param taskEntity        получатель, объект класса {@link TaskEntity}
     */
    public void toTaskEntity(UpdateTaskRequest updateTaskRequest, TaskEntity taskEntity) {
        modelMapper.map(updateTaskRequest, taskEntity);
    }

}
