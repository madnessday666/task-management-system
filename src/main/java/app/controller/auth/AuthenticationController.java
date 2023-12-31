package app.controller.auth;

import app.dto.auth.AuthenticationRequest;
import app.dto.auth.AuthenticationResponse;
import app.dto.auth.RegistrationRequest;
import app.dto.auth.RegistrationResponse;
import app.dto.error.ApiError;
import app.service.auth.impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Контроллер аутентификации и регистрации")
public class AuthenticationController {

    public static final String SIGN_IN = "/api/v1/auth/sign-in";
    public static final String SIGN_UP = "/api/v1/auth/sign-up";

    private final AuthenticationServiceImpl authenticationService;

    /**
     * Обрабатывает полученный запрос на аутентификацию.
     *
     * @param authenticationRequest запрос на аутентификацию.
     * @return {@link ResponseEntity} с телом {@link AuthenticationResponse} в случае успеха.
     */
    @Operation(
            summary = "Аутентификация в системе",
            description = "Вход в систему с учетными данными пользователя"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный вход",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AuthenticationResponse.class)
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
                            responseCode = "403",
                            description = """
                                    \t
                                    Если имя пользователя или пароль неверны
                                    \t
                                    Если пользователь заблокирован
                                    \t
                                    Если пользователь не активирован""",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )})
            })
    @PostMapping(SIGN_IN)
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.signIn(authenticationRequest));
    }

    /**
     * Обрабатывает полученный запрос на регистрацию.
     *
     * @param registrationRequest запрос на регистрацию.
     * @return {@link ResponseEntity} с телом {@link RegistrationResponse} в случае успеха.
     */
    @Operation(
            summary = "Регистрация в системе",
            description = "Регистрация в системе с новыми учетными данными пользователя"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Успешная регистрация",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = RegistrationResponse.class)
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
                            responseCode = "403",
                            description = "Если значение уникального поля (например, username) уже присутсвует в базе данных",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class)
                                    )}),
            })
    @PostMapping(SIGN_UP)
    public ResponseEntity<RegistrationResponse> signUp(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signUp(registrationRequest));
    }

}
