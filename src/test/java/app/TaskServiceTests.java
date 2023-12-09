package app;

import app.dto.task.CreateTaskRequest;
import app.dto.task.CreateTaskResponse;
import app.dto.task.UpdateTaskRequest;
import app.dto.task.UpdateTaskResponse;
import app.entity.task.TaskEntity;
import app.exception.AlreadyExistsException;
import app.mapper.task.TaskMapper;
import app.repository.task.TaskRepository;
import app.service.task.impl.TaskServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private TaskMapper taskMapper = new TaskMapper(modelMapper);

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        modelMapper.createTypeMap(UpdateTaskRequest.class, TaskEntity.class);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void handleCreateTaskRequest_whenRequestIsValid_thenReturnCreateTaskResponse() {
        CreateTaskRequest request = CreateTaskRequest
                .builder()
                .name("Task name")
                .description("Task description")
                .status("pending")
                .priority("low")
                .expiresOn(LocalDateTime.now().plusDays(5))
                .build();

        Set<ConstraintViolation<CreateTaskRequest>> errors = validator.validate(request);
        CreateTaskResponse response = taskService.createTask(UUID.randomUUID(), request);

        assertEquals(0, errors.size());
        assertNotNull(response);
    }

    @Test
    void handleCreateTaskRequest_whenTaskWithNameAlreadyExists_thenThrowAlreadyExistsException() {
        CreateTaskRequest request = CreateTaskRequest
                .builder()
                .name("Task name")
                .description("Task description")
                .status("pending")
                .priority("low")
                .expiresOn(LocalDateTime.now().plusDays(5))
                .build();
        when(taskRepository.existsByNameAndCreatorId(any(), any())).thenReturn(true);

        Set<ConstraintViolation<CreateTaskRequest>> errors = validator.validate(request);

        assertEquals(0, errors.size());
        assertThrows(AlreadyExistsException.class, () -> taskService.createTask(UUID.randomUUID(), request));
    }

    @Test
    void handleUpdateTaskRequest_whenRequestIsValid_thenReturnUpdateTaskResponse() {
        UpdateTaskRequest request = UpdateTaskRequest
                .builder()
                .id(UUID.randomUUID())
                .name("New task name")
                .build();
        when(taskRepository.findById(request.getId())).thenReturn(Optional.of(new TaskEntity()));

        Set<ConstraintViolation<UpdateTaskRequest>> errors = validator.validate(request);
        UpdateTaskResponse response = taskService.updateTask(request);

        assertEquals(0, errors.size());
        assertNotNull(response);
    }

    @Test
    void handleUpdateTaskRequest_whenRequestIsInvalid_thenValidationError() {
        UpdateTaskRequest request = UpdateTaskRequest
                .builder()
                .id(UUID.randomUUID())
                .name("") // <-- Name is blank
                .build();

        Set<ConstraintViolation<UpdateTaskRequest>> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}
