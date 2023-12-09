package app;

import app.dto.auth.AuthenticationRequest;
import app.dto.auth.AuthenticationResponse;
import app.dto.auth.RegistrationRequest;
import app.dto.auth.RegistrationResponse;
import app.entity.user.UserEntity;
import app.exception.AlreadyExistsException;
import app.mapper.user.UserMapper;
import app.repository.user.UserRepository;
import app.security.jwt.JwtService;
import app.service.auth.impl.AuthenticationServiceImpl;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Spy
    private UserMapper userMapper = new UserMapper(modelMapper);

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    UserServiceImpl userService;

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void handleRegistrationRequest_whenRequestIsValid_thenReturnRegistrationResponse() {
        RegistrationRequest request = RegistrationRequest
                .builder()
                .username("myusername")
                .password("Mypass123!")
                .name("Myname")
                .email("myemail@domain.com")
                .timestamp(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<RegistrationRequest>> errors = validator.validate(request);
        RegistrationResponse response = userService.createUser(request);

        assertEquals(0, errors.size());
        assertNotNull(response);
    }

    @Test
    void handleRegistrationRequest_whenRequestFieldsIsNotValid_thenValidationError() {
        RegistrationRequest request = RegistrationRequest
                .builder()
                .username("myusername")
                .password("mypass123!") // <-- Password must contain one uppercase letter
                .name("Myname")
                .email("myemail@domain.com")
                .build();

        Set<ConstraintViolation<RegistrationRequest>> errors = validator.validate(request);

        assertNotEquals(errors.size(), 0);
    }

    @Test
    void handleRegistrationRequest_whenRequestFieldsIsNotPresent_thenValidationError() {
        RegistrationRequest request = RegistrationRequest
                .builder()
                // <-- "Username" field not present
                .password("Mypass123!")
                .name("Myname")
                .email("myemail@domain.com")
                .build();

        Set<ConstraintViolation<RegistrationRequest>> errors = validator.validate(request);

        assertNotEquals(errors.size(), 0);
    }

    @Test
    void handleRegistrationRequest_whenUsernameAlreadyTaken_thenThrowAlreadyExistsException() {
        RegistrationRequest request = RegistrationRequest
                .builder()
                .username("myusername")
                .password("Mypass123!")
                .name("Myname")
                .email("myemail@domain.com")
                .build();
        when(userRepository.existsByUsername(any())).thenReturn(true);

        Set<ConstraintViolation<RegistrationRequest>> errors = validator.validate(request);

        assertEquals(0, errors.size());
        assertThrows(AlreadyExistsException.class, () -> userService.createUser(request));
    }

    @Test
    void handleAuthenticationRequest_whenRequestIsValid_thenReturnAuthenticationResponse() {
        UserEntity user = UserEntity.builder().id(UUID.randomUUID()).build();
        Authentication authentication = mock(Authentication.class);
        AuthenticationRequest request = AuthenticationRequest
                .builder()
                .username("myusername")
                .password("Mypass123!")
                .build();
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateToken(any(), any())).thenReturn("jwt");

        Set<ConstraintViolation<AuthenticationRequest>> errors = validator.validate(request);
        AuthenticationResponse authenticationResponse = authenticationService.signIn(request);

        assertEquals(0, errors.size());
        assertNotNull(authenticationResponse);
    }

    @Test
    void handleAuthenticationRequest_whenRequestIsValidAndUsernameIsInvalid_thenThrowBadCredentialsException() {
        AuthenticationRequest request = AuthenticationRequest
                .builder()
                .username("someusername")
                .password("Mypass123!")
                .build();
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid username or password"));

        Set<ConstraintViolation<AuthenticationRequest>> errors = validator.validate(request);

        assertEquals(0, errors.size());
        assertThrows(BadCredentialsException.class, () -> authenticationService.signIn(request));
    }

}
