package app.repository.user;

import app.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Интерфейс, описывающий методы взаимодействия с базой данных для класса {@link UserEntity}.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, PagingAndSortingRepository<UserEntity, UUID> {



}
