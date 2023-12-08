package app.controller.user;

import app.controller.ControllerHelper;
import app.dto.error.ApiError;
import app.dto.user.*;
import app.entity.user.UserEntity;
import app.mapper.user.UserMapper;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Контроллер для управления пользователями")
public class UserController {

    public static final String GET_USERS = "/api/v1/users";
    public static final String GET_USER_BY_ID = "/api/v1/users/{user_id}";

    public static final String UPDATE_USER = "/api/v1/users";

    public static final String DELETE_USER = "/api/v1/users";

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    private final ControllerHelper controllerHelper;

    /**
     * Обрабаотыает полученный запрос на получение списка пользователей.
     *
     * @param page номер страницы.
     * @param size количество элементов на странице.
     * @return {@link ResponseEntity} с телом {@link List} объектов {@link UserDto} в случае успеха.
     */
    @Operation(
            summary = "Получение списка пользователей",
            description = "Позволяет получить список пользователей"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список пользователей успешно получен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
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
    @GetMapping(GET_USERS)
    public ResponseEntity<List<UserDto>> getUsers(
            @Schema(description = "Номер страницы",
                    defaultValue = "0",
                    minimum = "0") @RequestParam int page,
            @Schema(description = "Количество элементов на странице",
                    defaultValue = "5",
                    minimum = "1") @RequestParam int size) {
        List<UserEntity> users = userService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(userMapper.toDtoList(users));
    }

    /**
     * Обрабатывает полученный запрос на получие пользователя с указанным id.
     *
     * @param userId id пользователя.
     * @return {@link ResponseEntity} с телом {@link UserDto} в случае успеха.
     */
    @Operation(
            summary = "Получение пользователя по id",
            description = "Позволяет получить пользователя с конкретным id"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно получен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserDto.class)
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
                            description = "Если пользователь с указанным id не найден",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @GetMapping(GET_USER_BY_ID)
    public ResponseEntity<UserDto> getUserById(@Schema(description = "id пользователя")
                                               @PathVariable("user_id") UUID userId) {
        UserEntity user = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    /**
     * Обрабатывает полученный запрос на обновление пользователя.
     *
     * @param updateUserRequest  запрос на обновление пользователя.
     * @param httpServletRequest информация о HTTP запросе.
     * @return {@link ResponseEntity} с телом {@link UpdateUserResponse} в случае успеха.
     */
    @Operation(
            summary = "Обновление пользователя",
            description = "Позволяет обновить данные пользователя пользователя"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно обновлен",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UpdateUserResponse.class)
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
                                    Если произведена попытка обновить данные другого пользователя""",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если пользователь с указанным id не найден",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Если значение уникальных полей запроса (например, username) уже присутсвуют в базе данных",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @PatchMapping(UPDATE_USER)
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest,
                                                         HttpServletRequest httpServletRequest) {
        controllerHelper.checkRequest(updateUserRequest, httpServletRequest);
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }

    /**
     * Обрабатывает полученный запрос на удаление пользователя.
     *
     * @param deleteUserRequest  запрос на удаление пользователя.
     * @param httpServletRequest информация о HTTP запросе.
     * @return {@link ResponseEntity} с телом {@link DeleteUserResponse} в случае успеха.
     */
    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя из системы"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно удален",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = DeleteUserResponse.class)
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
                                    Если произведена попытка удалить данные другого пользователя
                                    \t
                                    Если указанные в запросе данные не прошли проверку на принадлежность к пользователю (например, не совпали пароли)""",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если пользователь с указанным id не найден",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<DeleteUserResponse> deleteUser(@RequestBody @Valid DeleteUserRequest deleteUserRequest,
                                                         HttpServletRequest httpServletRequest) {
        controllerHelper.checkRequest(deleteUserRequest, httpServletRequest);
        return ResponseEntity.ok(userService.deleteUser(deleteUserRequest));
    }

}
