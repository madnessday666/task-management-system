package app.mapper.user;

import app.dto.auth.RegistrationRequest;
import app.dto.auth.RegistrationResponse;
import app.dto.user.UpdateUserRequest;
import app.dto.user.UpdateUserResponse;
import app.dto.user.UserDto;
import app.entity.user.UserEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для выполнения различных преобразований над объектом {@link UserEntity}.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    /**
     * Инициализация типов преобразований.
     */
    @PostConstruct
    private void init() {
        modelMapper.createTypeMap(RegistrationRequest.class, UserEntity.class);
        modelMapper
                .createTypeMap(UserEntity.class, RegistrationResponse.class)
                .addMappings(mapping -> mapping.skip(
                        UserEntity::getPassword,
                        RegistrationResponse::setPassword));
        modelMapper
                .createTypeMap(UpdateUserRequest.class, UserEntity.class)
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateUserRequest::getUsername, UserEntity::setUsername))
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateUserRequest::getPassword, UserEntity::setPassword))
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateUserRequest::getName, UserEntity::setName))
                .addMappings(mapper -> mapper.when(Conditions.isNull()).skip(UpdateUserRequest::getEmail, UserEntity::setEmail));
        modelMapper.createTypeMap(UserEntity.class, UpdateUserRequest.class);
        modelMapper
                .createTypeMap(UserEntity.class, UpdateUserResponse.class)
                .addMappings(mapping -> mapping.skip(
                        UserEntity::getPassword,
                        UpdateUserResponse::setPassword));
        modelMapper.createTypeMap(UserEntity.class, UserDto.class);
    }


    /**
     * Метод преобразования {@link RegistrationRequest} в {@link UserEntity}.
     *
     * @param registrationRequest источник, объект класса {@link RegistrationRequest}.
     * @return {@link UserEntity} со значениями полей от переданного {@link RegistrationRequest}.
     */
    public UserEntity toUserEntity(RegistrationRequest registrationRequest) {
        return modelMapper.map(registrationRequest, UserEntity.class);
    }

    /**
     * Метод преобразования {@link UserEntity} в {@link RegistrationResponse}.
     *
     * @param userEntity источник, объект класса {@link UserEntity}.
     * @return {@link RegistrationResponse} со значениями полей от переданного {@link UserEntity}.
     */
    public RegistrationResponse toRegistrationResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, RegistrationResponse.class);
    }

    /**
     * Метод преобразования {@link UserEntity} в {@link UpdateUserResponse}.
     *
     * @param userEntity источник, объект класса {@link UserEntity}.
     * @return {@link UpdateUserResponse} со значениями полей от переданного {@link UserEntity}.
     */
    public UpdateUserResponse toUpdateUserResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, UpdateUserResponse.class);
    }

    /**
     * Метод преобразования {@link UserEntity} в {@link UserDto}.
     *
     * @param userEntity источник, объект класса {@link UserEntity}.
     * @return {@link UserDto} со значениями полей от переданного {@link UserEntity}.
     */
    public UserDto toDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    /**
     * Метод преобразования {@link List} объектов {@link UserEntity} в {@link List} объектов {@link UserDto}.
     *
     * @param userEntities источник, список объектов класса {@link UserEntity}.
     * @return {@link List} объектов {@link UserEntity} со значениями полей от переданных {@link UserEntity}.
     */
    public List<UserDto> toDtoList(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::toDto).toList();
    }

    /**
     * Метод передачи значения полей {@link UpdateUserRequest} объекту {@link UserEntity}. Поля со значением {@literal null} будут пропущены.
     *
     * @param updateUserRequest источник, объект класса {@link UpdateUserRequest}
     * @param userEntity        получатель, объект класса {@link UserEntity}
     */
    public void toUserEntity(UpdateUserRequest updateUserRequest, UserEntity userEntity) {
        modelMapper.map(updateUserRequest, userEntity);
    }

}
