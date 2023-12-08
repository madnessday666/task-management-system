package app.mapper.task;

import app.dto.task_comment.CreateTaskCommentResponse;
import app.dto.task_comment.TaskCommentDto;
import app.entity.task_comment.TaskCommentEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для выполнения различных преобразований над объектом {@link TaskCommentEntity}.
 */
@Component
@RequiredArgsConstructor
public class TaskCommentMapper {

    private final ModelMapper modelMapper;

    /**
     * Инициализация типов преобразований.
     */
    @PostConstruct
    private void init() {
        modelMapper
                .createTypeMap(TaskCommentEntity.class, TaskCommentDto.class)
                .addMappings(mapping -> mapping.map(source -> source.getTask().getId(), TaskCommentDto::setTaskId));
        modelMapper
                .createTypeMap(TaskCommentEntity.class, CreateTaskCommentResponse.class)
                .addMappings(mapping -> mapping.map(source -> source.getTask().getId(), CreateTaskCommentResponse::setTaskId));
    }

    /**
     * Метод преобразования {@link TaskCommentEntity} в {@link TaskCommentDto}.
     *
     * @param taskCommentEntity источник, объект класса {@link TaskCommentEntity}.
     * @return {@link TaskCommentDto} со значениями полей от переданного {@link TaskCommentEntity}.
     */
    public TaskCommentDto toDto(TaskCommentEntity taskCommentEntity) {
        return modelMapper.map(taskCommentEntity, TaskCommentDto.class);
    }

    /**
     * Метод преобразования {@link List} {@link TaskCommentEntity} в {@link TaskCommentDto}.
     *
     * @param taskCommentEntities источник, список объектов класса {@link TaskCommentEntity}.
     * @return {@link List} {@link TaskCommentDto} со значениями полей от переданных {@link TaskCommentEntity}.
     */
    public List<TaskCommentDto> toDtoList(List<TaskCommentEntity> taskCommentEntities) {
        return taskCommentEntities.stream().map(this::toDto).toList();
    }

    /**
     * Метод преобразования {@link TaskCommentEntity} в {@link CreateTaskCommentResponse}.
     *
     * @param taskCommentEntity источник, объект класса {@link TaskCommentEntity}.
     * @return {@link CreateTaskCommentResponse} со значениями полей от переданного {@link TaskCommentEntity}.
     */
    public CreateTaskCommentResponse toCreateTaskCommentResponse(TaskCommentEntity taskCommentEntity) {
        return modelMapper.map(taskCommentEntity, CreateTaskCommentResponse.class);
    }

}
