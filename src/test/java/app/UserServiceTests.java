package app;

import app.dto.user.DeleteUserRequest;
import app.dto.user.DeleteUserResponse;
import app.dto.user.UpdateUserRequest;
import app.dto.user.UpdateUserResponse;
import app.entity.user.UserEntity;
import app.mapper.user.UserMapper;
import app.repository.user.UserRepository;
import app.service.user.impl.UserServiceImpl;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private UserMapper userMapper = new UserMapper(modelMapper);

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        modelMapper.createTypeMap(UpdateUserRequest.class, UserEntity.class);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void handleUpdateUserRequest_whenRequestIsValid_thenReturnUpdateUserResponse() {
        UpdateUserRequest request = UpdateUserRequest
                .builder()
                .id(UUID.randomUUID())
                .name("Myname")
                .build();
        when(userRepository.findById(request.getId())).thenReturn(Optional.of(new UserEntity()));

        Set<ConstraintViolation<UpdateUserRequest>> errors = validator.validate(request);
        UpdateUserResponse response = userService.updateUser(request);

        assertEquals(0, errors.size());
        assertNotNull(response);
    }

    @Test
    void handleUpdateUserRequest_whenRequestIsInvalid_thenValidationError() {

        UpdateUserRequest request = UpdateUserRequest
                .builder()
                .id(UUID.randomUUID())
                .name("") // <-- Name is blank
                .build();

        Set<ConstraintViolation<UpdateUserRequest>> errors = validator.validate(request);

        assertNotEquals(0, errors.size());
    }

    @Test
    void handleDeleteUserRequest_whenRequestIsValid_thenReturnDeleteUserResponse() {

        DeleteUserRequest request = DeleteUserRequest
                .builder()
                .id(UUID.randomUUID())
                .password("valid_password")
                .build();
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        Set<ConstraintViolation<DeleteUserRequest>> errors = validator.validate(request);
        DeleteUserResponse deleteUserResponse = userService.deleteUser(request);

        assertNotEquals(0, errors.size());
        assertNotNull(deleteUserResponse);
    }

    @Test
    void handleDeleteUserRequest_whenPasswordInRequestNotMatches_thenThrowBadCredentialsExceptionException() {

        DeleteUserRequest request = DeleteUserRequest
                .builder()
                .id(UUID.randomUUID())
                .password("invalid_password")
                .build();
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        Set<ConstraintViolation<DeleteUserRequest>> errors = validator.validate(request);

        assertNotEquals(0, errors.size());
        assertThrows(BadCredentialsException.class, () -> userService.deleteUser(request));
    }

}
