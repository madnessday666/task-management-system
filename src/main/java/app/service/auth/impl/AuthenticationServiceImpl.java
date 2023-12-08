package app.service.auth.impl;

import app.dto.auth.AuthenticationRequest;
import app.dto.auth.AuthenticationResponse;
import app.dto.auth.RegistrationRequest;
import app.dto.auth.RegistrationResponse;
import app.entity.user.UserEntity;
import app.exception.AlreadyExistsException;
import app.security.jwt.JwtService;
import app.service.auth.AuthenticationService;
import app.service.user.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Класс, реализующий методы {@link AuthenticationService}.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final UserServiceImpl userService;

    /**
     * {@inheritDoc}
     *
     * @see AuthenticationService#signIn(AuthenticationRequest)
     */
    @Override
    public AuthenticationResponse signIn(AuthenticationRequest authenticationRequest)
            throws DisabledException, LockedException, BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        UserEntity user = ((UserEntity) authentication.getPrincipal());
        String jwt = jwtService.generateToken(
                Map.of("userId", user.getId()),
                (UserDetails) authentication.getPrincipal()
        );
        return new AuthenticationResponse(jwt, LocalDateTime.now());
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthenticationService#signUp(RegistrationRequest)
     */
    @Override
    public RegistrationResponse signUp(RegistrationRequest registrationRequest) throws AlreadyExistsException {
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        registrationRequest.setPassword(encodedPassword);
        return userService.createUser(registrationRequest);
    }

}
