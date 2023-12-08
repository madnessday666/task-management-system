package app.controller;

import app.dto.task.CreateTaskRequest;
import app.dto.task.DeleteTaskRequest;
import app.dto.task.UpdateTaskRequest;
import app.dto.user.DeleteUserRequest;
import app.dto.user.UpdateUserRequest;
import app.entity.task.TaskPriority;
import app.entity.task.TaskStatus;
import app.exception.*;
import app.security.jwt.JwtService;
import app.service.task.impl.TaskServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Класс для определения методов предварительной проверки запросов.
 */
@Component
@RequiredArgsConstructor
public class ControllerHelper {

    private final JwtService jwtService;
    private final TaskServiceImpl taskService;

    /**
     * Метод для проверки запроса.
     *
     * @param updateUserRequest  запрос на обновление пользователя.
     * @param httpServletRequest информация о HTTP запросе.
     * @throws PermissionDeniedException если у пользователя недостаточно прав для выполнения запроса.
     */
    public void checkRequest(UpdateUserRequest updateUserRequest, HttpServletRequest httpServletRequest) throws PermissionDeniedException {
        UUID currentUserId = this.getUserIdFromHttpServletRequest(httpServletRequest);
        boolean areCurrentUserIdAndUserIdEquals = Objects.equals(currentUserId, updateUserRequest.getId());
        if (!areCurrentUserIdAndUserIdEquals) {
            throw new PermissionDeniedException("Current user id and request user id are not equals");
        }
    }

    /**
     * Метод для проверки запроса.
     *
     * @param deleteUserRequest  запрос на удаление пользователя.
     * @param httpServletRequest информация о HTTP запросе.
     * @throws PermissionDeniedException если у пользователя недостаточно прав для выполнения запроса.
     */
    public void checkRequest(DeleteUserRequest deleteUserRequest, HttpServletRequest httpServletRequest) throws PermissionDeniedException {
        UUID currentUserId = this.getUserIdFromHttpServletRequest(httpServletRequest);
        boolean areCurrentUserIdAndUserIdEquals = Objects.equals(currentUserId, deleteUserRequest.getId());
        if (!areCurrentUserIdAndUserIdEquals) {
            throw new PermissionDeniedException("Current user id and request user id are not equals");
        }
    }

    /**
     * Метод для проверки запроса.
     *
     * @param deleteTaskRequest  запрос на удаление задачи.
     * @param httpServletRequest информация о HTTP запросе.
     * @throws PermissionDeniedException если у пользователя недостаточно прав для выполнения запроса.
     */
    public void checkRequest(DeleteTaskRequest deleteTaskRequest, HttpServletRequest httpServletRequest) throws PermissionDeniedException {
        UUID currentUserId = this.getUserIdFromHttpServletRequest(httpServletRequest);
        boolean isUserTaskCreator = taskService.getIsTaskExistsByIdAndCreatorId(deleteTaskRequest.getId(), currentUserId);
        if (!isUserTaskCreator) {
            throw new PermissionDeniedException("User is not task creator");
        }
    }

    /**
     * Метод для проверки запроса.
     *
     * @param taskId             id задачи.
     * @param httpServletRequest информация о HTTP запросе.
     * @throws NotFoundException         если задача с указанным id не существует.
     * @throws PermissionDeniedException если у пользователя недостаточно прав для выполнения запроса.
     */
    public void checkRequest(UUID taskId, HttpServletRequest httpServletRequest) throws PermissionDeniedException {
        if (taskService.getIsTaskExistsById(taskId)) {
            UUID currentUserId = this.getUserIdFromHttpServletRequest(httpServletRequest);
            boolean isUserTaskCreator = taskService.getIsTaskExistsByIdAndCreatorId(taskId, currentUserId);
            boolean isUserTaskExecutor = taskService.getIsTaskExistsByIdAndExecutorId(taskId, currentUserId);
            if (!isUserTaskCreator && !isUserTaskExecutor) {
                throw new PermissionDeniedException("User is not related to the task");
            }
        } else {
            throw new NotFoundException("Task", "id", taskId);
        }

    }

    /**
     * Метод для проверки запроса. В случае, если прав недостаточно для изменения всех полей задачи - в запросе останутся
     * только те поля, на изменение которых у пользователя есть права.
     *
     * @param updateTaskRequest  запрос на обновление задачи.
     * @param httpServletRequest информация о HTTP запросе.
     * @throws PermissionDeniedException      если у пользователя недостаточно прав для выполнения запроса.
     * @throws AlreadyExistsException         если задача с указанным в запросе именем уже существует у текущего пользователя.
     * @throws InvalidValueSelectionException если указанные в запросе данные некорректны.
     */
    public void checkAndModifyRequest(UpdateTaskRequest updateTaskRequest, HttpServletRequest httpServletRequest)
            throws PermissionDeniedException, AlreadyExistsException, InvalidValueSelectionException {
        UUID currentUserId = this.getUserIdFromHttpServletRequest(httpServletRequest);
        boolean isTaskWithNameExists = taskService.getIsTaskExistsByNameAndCreatorId(updateTaskRequest.getName(), currentUserId);
        if (isTaskWithNameExists) {
            throw new AlreadyExistsException("Task", "name", updateTaskRequest.getName());
        }
        this.checkAndModifyRequest(updateTaskRequest);
        boolean isUserTaskCreator = taskService.getIsTaskExistsByIdAndCreatorId(updateTaskRequest.getId(), currentUserId);
        boolean isUserTaskExecutor = taskService.getIsTaskExistsByIdAndExecutorId(updateTaskRequest.getId(), currentUserId);
        if (!isUserTaskCreator) {
            if (isUserTaskExecutor) {
                updateTaskRequest.setName(null);
                updateTaskRequest.setDescription(null);
                updateTaskRequest.setPriority(null);
                updateTaskRequest.setExecutorId(null);
                updateTaskRequest.setExpiresOn(null);
            } else {
                throw new PermissionDeniedException("User is not related to the task");
            }
        }
    }

    /**
     * Метод для проверки на соответствие строковых значений запроса объектам типа {@link Enum}.
     * Приводит строковые значения в запросе к верхнему регистру.
     *
     * @param request запрос.
     * @throws InvalidValueSelectionException если значения не соответствуют допустимым.
     */
    public void checkAndModifyRequest(Object request) throws InvalidValueSelectionException {
        if (request instanceof CreateTaskRequest) {
            if (((CreateTaskRequest) request).getStatus() != null) {
                try {
                    String status = ((CreateTaskRequest) request).getStatus().toUpperCase();
                    TaskStatus.valueOf(status);
                    ((CreateTaskRequest) request).setStatus(status);
                } catch (IllegalArgumentException exception) {
                    String values = Arrays
                            .stream(TaskStatus.values()).map(value -> value.toString().toLowerCase())
                            .collect(Collectors.joining(", "));
                    throw new InvalidValueSelectionException(((CreateTaskRequest) request).getStatus(), values);
                }
            }
            if (((CreateTaskRequest) request).getPriority() != null) {
                try {
                    String priority = ((CreateTaskRequest) request).getPriority().toUpperCase();
                    TaskPriority.valueOf(priority);
                    ((CreateTaskRequest) request).setPriority(priority);
                } catch (IllegalArgumentException exception) {
                    String values = Arrays
                            .stream(TaskPriority.values()).map(value -> value.toString().toLowerCase())
                            .collect(Collectors.joining(", "));
                    throw new InvalidValueSelectionException(((CreateTaskRequest) request).getPriority(), values);
                }
            }
        } else if (request instanceof UpdateTaskRequest) {
            if (((UpdateTaskRequest) request).getStatus() != null) {
                try {
                    String status = ((UpdateTaskRequest) request).getStatus().toUpperCase();
                    TaskStatus.valueOf(status);
                    ((UpdateTaskRequest) request).setStatus(status);
                } catch (IllegalArgumentException exception) {
                    String values = Arrays
                            .stream(TaskStatus.values()).map(value -> value.toString().toLowerCase())
                            .collect(Collectors.joining(", "));
                    throw new InvalidValueSelectionException(((UpdateTaskRequest) request).getStatus(), values);
                }
            }
            if (((UpdateTaskRequest) request).getPriority() != null) {
                try {
                    String priority = ((UpdateTaskRequest) request).getPriority().toUpperCase();
                    TaskPriority.valueOf(priority);
                    ((UpdateTaskRequest) request).setPriority(priority);
                } catch (IllegalArgumentException exception) {
                    String values = Arrays
                            .stream(TaskPriority.values()).map(value -> value.toString().toLowerCase())
                            .collect(Collectors.joining(", "));
                    throw new InvalidValueSelectionException(((UpdateTaskRequest) request).getPriority(), values);
                }
            }
        }
    }

    /**
     * Метод для извлечения id пользователя из HttpServletRequest.
     *
     * @param httpServletRequest информация о HTTP запросе.
     * @return id пользователя в формате {@link UUID}.
     * @throws AuthorizationHeaderNotPresentException если в запросе отсутствует заголовок Authorization или Bearer token
     */
    public UUID getUserIdFromHttpServletRequest(HttpServletRequest httpServletRequest) throws AuthorizationHeaderNotPresentException {
        Optional<Object> userId = jwtService.extractClaimFromHttpServletRequestHeader("userId", httpServletRequest);
        if (userId.isPresent()) {
            return UUID.fromString((String) userId.get());
        } else {
            throw new AuthorizationHeaderNotPresentException();
        }
    }

}
