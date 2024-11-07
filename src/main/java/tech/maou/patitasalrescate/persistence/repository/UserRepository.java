package tech.maou.patitasalrescate.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import tech.maou.patitasalrescate.persistence.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
