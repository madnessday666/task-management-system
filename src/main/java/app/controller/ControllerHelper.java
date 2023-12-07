package app.controller;

import app.dto.task.CreateTaskRequest;
import app.dto.task.DeleteTaskRequest;
import app.dto.task.UpdateTaskRequest;
import app.dto.user.DeleteUserRequest;
import app.dto.user.UpdateUserRequest;
import app.exception.AlreadyExistsException;
import app.exception.PermissionDeniedException;
import app.security.jwt.JwtService;
import app.service.task.impl.TaskServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ControllerHelper {

    private final JwtService jwtService;
    private final TaskServiceImpl taskService;

    public UpdateUserRequest checkRequest(UpdateUserRequest updateUserRequest, HttpServletRequest httpServletRequest)
            throws PermissionDeniedException, JwtException {
        Optional<Object> optional = jwtService.extractClaimFromHttpServletRequestHeader("userId", httpServletRequest);
        if (optional.isPresent()) {
            UUID currentUserId = UUID.fromString((String) optional.get());
            boolean areCurrentUserIdAndUserIdEquals = Objects.equals(currentUserId, updateUserRequest.getId());
            if (areCurrentUserIdAndUserIdEquals) {
                return updateUserRequest;
            } else {
                throw new PermissionDeniedException("Current user id and request user id are not equals");
            }
        } else {
            throw new JwtException("Jwt not present");
        }
    }

    public DeleteUserRequest checkRequest(DeleteUserRequest deleteUserRequest, HttpServletRequest httpServletRequest)
            throws PermissionDeniedException, JwtException {
        Optional<Object> optional = jwtService.extractClaimFromHttpServletRequestHeader("userId", httpServletRequest);
        if (optional.isPresent()) {
            UUID currentUserId = UUID.fromString((String) optional.get());
            boolean areCurrentUserIdAndUserIdEquals = Objects.equals(currentUserId, deleteUserRequest.getId());
            if (areCurrentUserIdAndUserIdEquals) {
                return deleteUserRequest;
            } else {
                throw new PermissionDeniedException("Current user id and request user id are not equals");
            }
        } else {
            throw new JwtException("Jwt not present");
        }
    }

    public CreateTaskRequest checkRequest(CreateTaskRequest createTaskRequest, HttpServletRequest httpServletRequest)
            throws PermissionDeniedException, JwtException {
        Optional<Object> optional = jwtService.extractClaimFromHttpServletRequestHeader("userId", httpServletRequest);
        if (optional.isPresent()) {
            UUID currentUserId = UUID.fromString((String) optional.get());
            boolean areUserIdAndCreatorIdEquals = Objects.equals(currentUserId, createTaskRequest.getCreatorId());
            if (areUserIdAndCreatorIdEquals) {
                return createTaskRequest;
            } else {
                throw new PermissionDeniedException("User id and creator id are not equals");
            }
        } else {
            throw new JwtException("Jwt not present");
        }
    }

    public DeleteTaskRequest checkRequest(DeleteTaskRequest deleteTaskRequest, HttpServletRequest httpServletRequest)
            throws PermissionDeniedException, JwtException {
        Optional<Object> optional = jwtService.extractClaimFromHttpServletRequestHeader("userId", httpServletRequest);
        if (optional.isPresent()) {
            UUID currentUserId = UUID.fromString((String) optional.get());
            boolean isUserTaskCreator = taskService.getIsTaskExistsByIdAndCreatorId(deleteTaskRequest.getId(), currentUserId);
            if (isUserTaskCreator) {
                return deleteTaskRequest;
            } else {
                throw new PermissionDeniedException("User is not task creator");
            }
        } else {
            throw new JwtException("Jwt not present");
        }
    }

    public UpdateTaskRequest checkAndModifyRequest(UpdateTaskRequest updateTaskRequest, HttpServletRequest httpServletRequest)
            throws PermissionDeniedException, JwtException, AlreadyExistsException {
        Optional<Object> optional = jwtService.extractClaimFromHttpServletRequestHeader("userId", httpServletRequest);
        if (optional.isPresent()) {
            UUID currentUserId = UUID.fromString((String) optional.get());
            boolean isTaskWithNameExists = taskService.getIsTaskExistsByNameAndCreatorId(updateTaskRequest.getName(), currentUserId);
            if (isTaskWithNameExists) {
                throw new AlreadyExistsException("Task", "name", updateTaskRequest.getName());
            }
            boolean isUserTaskCreator = taskService.getIsTaskExistsByIdAndCreatorId(updateTaskRequest.getId(), currentUserId);
            boolean isUserTaskExecutor = taskService.getIsTaskExistsByIdAndExecutorId(updateTaskRequest.getId(), currentUserId);
            if (isUserTaskCreator) {
                return updateTaskRequest;
            } else if (isUserTaskExecutor) {
                updateTaskRequest = UpdateTaskRequest
                        .builder()
                        .id(updateTaskRequest.getId())
                        .status(updateTaskRequest.getStatus())
                        .build();
                return updateTaskRequest;
            } else {
                throw new PermissionDeniedException("User is not related to the task");
            }
        } else {
            throw new JwtException("Jwt not present");
        }
    }

}
