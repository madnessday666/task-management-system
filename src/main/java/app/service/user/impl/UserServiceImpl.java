package app.service.user.impl;

import app.dto.auth.RegistrationRequest;
import app.dto.auth.RegistrationResponse;
import app.dto.user.DeleteUserRequest;
import app.dto.user.DeleteUserResponse;
import app.dto.user.UpdateUserRequest;
import app.dto.user.UpdateUserResponse;
import app.entity.user.UserEntity;
import app.entity.user.UserRole;
import app.exception.AlreadyExistsException;
import app.exception.NotFoundException;
import app.mapper.user.UserMapper;
import app.repository.user.UserRepository;
import app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     *
     * @see UserService#getUserById(UUID)
     */
    @Override
    public UserEntity getUserById(UUID userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "id", userId));
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getUserByUsername(String)
     */
    @Override
    public UserEntity getUserByUsername(String username) throws NotFoundException {
        return userRepository.getUserEntityByUsername(username).orElseThrow(() -> new NotFoundException("User", "username", username));
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#findAll(Pageable)
     */
    @Override
    public List<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getIsUserExistsById(UUID)
     */
    @Override
    public boolean getIsUserExistsById(UUID userId) {
        return userRepository.existsById(userId);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getIsUserExistsByUsername(String)
     */
    @Override
    public boolean getIsUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#getIsUserExistsByEmail(String)
     */
    @Override
    public boolean getIsUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#createUser(RegistrationRequest)
     */
    @Override
    public RegistrationResponse createUser(RegistrationRequest registrationRequest) throws AlreadyExistsException {
        this.checkIfTaken(registrationRequest.getUsername(), registrationRequest.getEmail());
        UserEntity user = userMapper.toUserEntity(registrationRequest);
        user.setRole(UserRole.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        UserEntity createdUser = userRepository.saveAndFlush(user);
        log.info("\nUser has been created: {}", createdUser);
        return userMapper.toRegistrationResponse(createdUser);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#updateUser(UpdateUserRequest)
     */
    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) throws AlreadyExistsException, NotFoundException {
        this.checkIfTaken(updateUserRequest.getUsername(), updateUserRequest.getEmail());
        UserEntity user = this.getUserById(updateUserRequest.getId());
        userMapper.toUserEntity(updateUserRequest, user);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.saveAndFlush(user);
        log.info("\nUser has been updated: {}", user);
        return userMapper.toUpdateUserResponse(user);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserService#deleteUser(DeleteUserRequest)
     */
    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) throws NotFoundException, BadCredentialsException {
        UserEntity user = this.getUserById(deleteUserRequest.getId());
        boolean isPasswordValid = passwordEncoder.matches(deleteUserRequest.getPassword(), user.getPassword());
        if (isPasswordValid) {
            UUID deletedUserId = userRepository.deleteUserById(deleteUserRequest.getId());
            log.info("\nUser with id {} has been deleted", deletedUserId);
            return new DeleteUserResponse(deletedUserId, LocalDateTime.now());
        } else {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    /**
     * Метод для проверки уникальности данных.
     *
     * @param username имя пользователя для входа в систему.
     * @param email    электронная почта пользователя.
     * @throws AlreadyExistsException если хотя бы одно из указанных значений присутствует в базе данных.
     */
    private void checkIfTaken(String username, String email) throws AlreadyExistsException {
        if (this.getIsUserExistsByUsername(username)) {
            throw new AlreadyExistsException("User", "username", username);
        } else if (this.getIsUserExistsByEmail(email)) {
            throw new AlreadyExistsException("User", "email", email);
        }
    }

}
