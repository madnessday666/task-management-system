package app.controller.user;

import app.controller.ControllerHelper;
import app.dto.user.*;
import app.entity.user.UserEntity;
import app.mapper.user.UserMapper;
import app.service.user.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String GET_USERS = "/api/v1/users";
    public static final String GET_USER_BY_ID = "/api/v1/users/{user_id}";

    public static final String UPDATE_USER = "/api/v1/users";

    public static final String DELETE_USER = "/api/v1/users";

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final ControllerHelper controllerHelper;

    @GetMapping(GET_USERS)
    public List<UserDto> getUsers(@RequestParam int page,
                                  @RequestParam int size) {
        List<UserEntity> users = userService.findAll(PageRequest.of(page, size));
        return userMapper.toDtoList(users);
    }

    @GetMapping(GET_USER_BY_ID)
    public UserDto getUserById(@PathVariable("user_id") UUID userId) {
        UserEntity user = userService.getUserById(userId);
        return userMapper.toDto(user);
    }

    @PatchMapping(UPDATE_USER)
    public UpdateUserResponse updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest,
                                         HttpServletRequest httpServletRequest) {
        updateUserRequest = controllerHelper.checkRequest(updateUserRequest, httpServletRequest);
        return userService.updateUser(updateUserRequest);
    }

    @DeleteMapping(DELETE_USER)
    public DeleteUserResponse deleteUser(@RequestBody @Valid DeleteUserRequest deleteUserRequest,
                                         HttpServletRequest httpServletRequest) {
        deleteUserRequest = controllerHelper.checkRequest(deleteUserRequest, httpServletRequest);
        return userService.deleteUser(deleteUserRequest);
    }

}
