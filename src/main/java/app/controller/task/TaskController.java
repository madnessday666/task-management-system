package app.controller.task;

import app.controller.ControllerHelper;
import app.dto.error.ApiError;
import app.dto.task.*;
import app.dto.task_comment.*;
import app.entity.task.TaskEntity;
import app.entity.task_comment.TaskCommentEntity;
import app.entity.user.UserEntity;
import app.mapper.task.TaskCommentMapper;
import app.mapper.task.TaskMapper;
import app.repository.task.TaskSpecification;
import app.service.task.impl.TaskServiceImpl;
import app.service.task_comment.impl.TaskCommentServiceImpl;
import app.service.user.impl.UserServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Контроллер для управления задачами")
public class TaskController {

    public static final String GET_TASKS = "/api/v1/tasks";
    public static final String GET_TASK_BY_ID = "/api/v1/tasks/{task_id}";
    public static final String GET_TASK_COMMENTS_BY_TASK_ID = "/api/v1/tasks/{task_id}/comments";

    public static final String CREATE_TASK = "/api/v1/tasks";
    public static final String CREATE_TASK_COMMENT = "/api/v1/tasks/{task_id}/comments";

    public static final String UPDATE_TASK = "/api/v1/tasks";

    public static final String DELETE_TASK = "/api/v1/tasks";
    public static final String DELETE_TASK_COMMENT = "/api/v1/tasks/{task_id}/comments";

    private final TaskServiceImpl taskService;
    private final TaskCommentServiceImpl taskCommentService;
    private final UserServiceImpl userService;

    private final TaskMapper taskMapper;
    private final TaskCommentMapper taskCommentMapper;

    private final ControllerHelper controllerHelper;

    /**
     * Обрабатывает полученный запрос на получение списка задач с указанным набором фильтров.
     *
     * @param searchFilter набор фильтров.
     * @param page         номер страницы.
     * @param size         количество элементов на страцние.
     * @return {@link ResponseEntity} с телом {@link List} объектов {@link TaskDto} в случае успеха.
     */
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

    /**
     * Обрабатывает запрос на получение задачи с указанным id.
     *
     * @param taskId id задачи.
     * @return {@link ResponseEntity} с телом {@link TaskDto} в случае успеха.
     */
    @Operation(
            summary = "Получение информации о задаче",
            description = "Позволяет получить информацию о задаче с указанным id"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно получена",
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
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если задача с указанным id не найдена",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @GetMapping(GET_TASK_BY_ID)
    public ResponseEntity<TaskDto> getTaskById(@Schema(description = "id задачи")
                                               @PathVariable("task_id") UUID taskId) {
        TaskEntity task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    @Operation(
            summary = "Получение списка комментариев к задаче",
            description = "Позволяет получить список комментариев к задаче"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список комментариев успешно получен",
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
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если задача с указанным id не найдена",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })

    @GetMapping(GET_TASK_COMMENTS_BY_TASK_ID)
    public ResponseEntity<List<TaskCommentDto>> getTaskCommentsByTaskId(@Schema(description = "id задачи", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                                                                        @PathVariable("task_id") UUID taskId,
                                                                        @Schema(description = "Номер страницы",
                                                                                defaultValue = "0",
                                                                                minimum = "0") @RequestParam int page,
                                                                        @Schema(description = "Количество элементов на странице",
                                                                                defaultValue = "5",
                                                                                minimum = "1") @RequestParam int size,
                                                                        HttpServletRequest httpServletRequest) {
        controllerHelper.checkRequest(taskId, httpServletRequest);
        List<TaskCommentEntity> comments = taskCommentService.getTaskCommentPageByTaskId(taskId, PageRequest.of(page, size));
        return ResponseEntity.ok(taskCommentMapper.toDtoList(comments));
    }

    /**
     * Обрабатывает полученный запрос на создание задачи.
     *
     * @param createTaskRequest  запрос на создание задачи.
     * @param httpServletRequest информация о HTTP запросе.
     * @return {@link ResponseEntity} с телом {@link CreateTaskResponse} в случае успеха.
     */
    @Operation(
            summary = "Создание задачи",
            description = "Позволяет создать новую задачу"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Задача успешно создана",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CreateTaskResponse.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Если в запросе присутствуют недопустимые значения",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
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
                            responseCode = "404",
                            description = "Если исполнитель с указанным в запросе id не существует",
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
    public ResponseEntity<CreateTaskResponse> createTask(@RequestBody @Valid CreateTaskRequest createTaskRequest,
                                                         HttpServletRequest httpServletRequest) {
        controllerHelper.checkAndModifyRequest(createTaskRequest);
        UUID creatorId = controllerHelper.getUserIdFromHttpServletRequest(httpServletRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(creatorId, createTaskRequest));
    }

    /**
     * Обрабатывает полученный запрос на создание комментария.
     *
     * @param createTaskCommentRequest запрос на создание комментария1.
     * @param httpServletRequest       информация о HTTP запросе.
     * @return {@link ResponseEntity} с телом {@link CreateTaskCommentResponse} в случае успеха.
     */
    @Operation(
            summary = "Создание комментария к задаче",
            description = "Позволяет создать комментарий к задаче"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Комментарий успешно создан",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CreateTaskCommentResponse.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Если в запросе присутствуют недопустимые значения",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
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
                            description = """
                                    \t
                                    Если JWT просрочен или некорректен
                                    \t
                                    Если пользователь не является создателем или исполнителем задачи""",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = """
                                    \t
                                    Если пользователь с указанным id не существует
                                    \t
                                    Если задача с указанным id не существует
                                     """,
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @PostMapping(CREATE_TASK_COMMENT)
    public ResponseEntity<CreateTaskCommentResponse> createTaskComment(@Schema(description = "id задачи", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                                                                       @PathVariable("task_id") UUID taskId,
                                                                       @RequestBody @Valid CreateTaskCommentRequest createTaskCommentRequest,
                                                                       HttpServletRequest httpServletRequest) {
        controllerHelper.checkRequest(taskId, httpServletRequest);
        TaskEntity task = taskService.getTaskById(taskId);
        UserEntity user = userService.getUserById(controllerHelper.getUserIdFromHttpServletRequest(httpServletRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCommentService.createTaskComment(createTaskCommentRequest, task, user));
    }

    /**
     * Обрабатывает полученный запрос на обновление задачи.
     *
     * @param updateTaskRequest  запрос на обновление задачи.
     * @param httpServletRequest информация о HTTP запросе.
     * @return {@link ResponseEntity} с телом {@link UpdateTaskResponse} в случае успеха.
     */
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
                            responseCode = "400",
                            description = "Если в запросе присутствуют недопустимые значения",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
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
                            description = """
                                     \t
                                    Если JWT просрочен или некорректен
                                     \t
                                    Если пользователь не является создателем или исполнителем задачи""",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = """
                                     \t
                                    Если задача с указанным id не существует
                                     \t
                                    Если исполнитель с указанным в запросе id не существует""",
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
    public ResponseEntity<UpdateTaskResponse> updateTask(@RequestBody @Valid UpdateTaskRequest updateTaskRequest,
                                                         HttpServletRequest httpServletRequest) {
        controllerHelper.checkAndModifyRequest(updateTaskRequest, httpServletRequest);
        return ResponseEntity.ok(taskService.updateTask(updateTaskRequest));
    }

    /**
     * Обрабатывает полученный запрос на удаление задачи.
     *
     * @param deleteTaskRequest  запрос на удаление.
     * @param httpServletRequest информация о HTTP запросе.
     * @return {@link ResponseEntity} с телом {@link DeleteTaskResponse} в случае успеха.
     */
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
                            responseCode = "400",
                            description = "Если в запросе присутствуют недопустимые значения",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
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
                            description = """
                                     \t
                                    Если JWT просрочен или некорректен
                                     \t
                                    Если пользователь не является создателем задачи""",
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
    public ResponseEntity<DeleteTaskResponse> deleteTask(@RequestBody @Valid DeleteTaskRequest deleteTaskRequest,
                                                         HttpServletRequest httpServletRequest) {
        controllerHelper.checkRequest(deleteTaskRequest, httpServletRequest);
        return ResponseEntity.ok(taskService.deleteTask(deleteTaskRequest));
    }

    /**
     * Обрабатывает полученный запрос на удаление комментария.
     *
     * @param deleteTaskCommentRequest запрос на удаление.
     * @param httpServletRequest       информация о HTTP запросе.
     * @return {@link ResponseEntity} с телом {@link DeleteTaskCommentResponse} в случае успеха.
     */
    @Operation(
            summary = "Удаление комментария к задаче",
            description = "Позволяет удалить существующий комментарий к задаче"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарий успешно удален",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = DeleteTaskCommentResponse.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Если в запросе присутствуют недопустимые значения",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
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
                            description = """
                                     \t
                                    Если JWT просрочен или некорректен
                                     \t
                                    Если пользователь не является создателем комментария или задачи""",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = """
                                     \t
                                    Если задача с указанным id не существует
                                     \t
                                    Если комментарий с указанным id не существует""",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
            })
    @DeleteMapping(DELETE_TASK_COMMENT)
    public ResponseEntity<DeleteTaskCommentResponse> deleteTaskComment(@Schema(description = "id задачи", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                                                                       @PathVariable("task_id") UUID taskId,
                                                                       @RequestBody @Valid DeleteTaskCommentRequest deleteTaskCommentRequest,
                                                                       HttpServletRequest httpServletRequest) {
        controllerHelper.checkRequest(taskId, httpServletRequest);
        return ResponseEntity.ok(taskCommentService.deleteTaskComment(deleteTaskCommentRequest));
    }

}
