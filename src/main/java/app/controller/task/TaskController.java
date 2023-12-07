package app.controller.task;

import app.controller.ControllerHelper;
import app.dto.error.ApiError;
import app.dto.task.*;
import app.entity.task.TaskEntity;
import app.mapper.task.TaskMapper;
import app.repository.task.TaskSpecification;
import app.service.task.impl.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Контроллер для управления задачами")
public class TaskController {

    public static final String GET_TASKS = "/api/v1/tasks";

    public static final String CREATE_TASK = "/api/v1/tasks";

    public static final String UPDATE_TASK = "/api/v1/tasks";

    public static final String DELETE_TASK = "/api/v1/tasks";

    private final TaskServiceImpl taskService;
    private final TaskMapper taskMapper;
    private final ControllerHelper controllerHelper;

    @Operation(
            summary = "Получение списка задач",
            description = "Позволяет получить список задач по указанным критериям"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список задач успешно получен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TaskDto.class))
                                    )}),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Если в запросе отсутствует заголовок Authorization или Bearer token",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Если JWT просрочен или некорректен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @GetMapping(GET_TASKS)
    public ResponseEntity<List<TaskDto>> getTasksByCreatorIdWithSpecs(@ParameterObject TaskSearchFilterDto searchFilter,
                                                                      @Schema(description = "Номер страницы",
                                                                              defaultValue = "0",
                                                                              minimum = "0") @RequestParam int page,
                                                                      @Schema(description = "Количество элементов на странице",
                                                                              defaultValue = "5",
                                                                              minimum = "1") @RequestParam int size) {
        List<TaskEntity> tasks = taskService.getTaskPage(TaskSpecification.filterBy(searchFilter), PageRequest.of(page, size));
        return ResponseEntity.ok(taskMapper.toDtoList(tasks));
    }

    @Operation(
            summary = "Создание задачи",
            description = "Позволяет создать новую задачу"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно создана",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CreateTaskResponse.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Если в запросе отсутствует заголовок Authorization или Bearer token",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Если JWT просрочен или некорректен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Если задача с указанным именем уже присутствует у пользователя",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @PostMapping(CREATE_TASK)
    public ResponseEntity<CreateTaskResponse> createTask(@ParameterObject @RequestBody @Valid CreateTaskRequest createTaskRequest,
                                                         HttpServletRequest httpServletRequest) {
        createTaskRequest = controllerHelper.checkRequest(createTaskRequest, httpServletRequest);
        return ResponseEntity.ok(taskService.createTask(createTaskRequest));
    }

    @Operation(
            summary = "Обновление задачи",
            description = "Позволяет обновить существующую задачу задачу"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно обновлена",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UpdateTaskResponse.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Если в запросе отсутствует заголовок Authorization или Bearer token",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Если JWT просрочен или некорректен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Если пользователь не является создателем или исполнителем задачи",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если задача с указанным id не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Если задача с указанным именем уже присутствует у пользователя",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @PatchMapping(UPDATE_TASK)
    public ResponseEntity<UpdateTaskResponse> updateTask(@ParameterObject @RequestBody @Valid UpdateTaskRequest updateTaskRequest,
                                                         HttpServletRequest httpServletRequest) {
        updateTaskRequest = controllerHelper.checkAndModifyRequest(updateTaskRequest, httpServletRequest);
        return ResponseEntity.ok(taskService.updateTask(updateTaskRequest));
    }

    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет удалить существующую задачу"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно удалена",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = DeleteTaskResponse.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Если в запросе отсутствует заголовок Authorization или Bearer token",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Если JWT просрочен или некорректен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Если пользователь не является создателем задачи",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если задача с указанным id не существует",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
            })
    @DeleteMapping(DELETE_TASK)
    public ResponseEntity<DeleteTaskResponse> deleteTask(@ParameterObject @RequestBody @Valid DeleteTaskRequest deleteTaskRequest,
                                                         HttpServletRequest httpServletRequest) {
        deleteTaskRequest = controllerHelper.checkRequest(deleteTaskRequest, httpServletRequest);
        return ResponseEntity.ok(taskService.deleteTask(deleteTaskRequest));
    }

}
