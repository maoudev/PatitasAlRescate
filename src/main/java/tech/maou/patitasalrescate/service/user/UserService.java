package tech.maou.patitasalrescate.service.user;

import tech.maou.patitasalrescate.exception.DuplicateFieldException;
import tech.maou.patitasalrescate.exception.UserNotFoundException;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserRegistrationDTO;
import tech.maou.patitasalrescate.persistence.entity.UserEntity;

import java.util.UUID;

public interface UserService {
    void save(UserRegistrationDTO userDTO) throws DuplicateFieldException;
    void delete(UUID uuid);
    UserDTO findByUsername(String username) throws UserNotFoundException;
    UserDTO findByEmail(String email) throws UserNotFoundException;
    UserEntity findUserEntityByUsername(String username) throws UserNotFoundException;
    UserEntity getCurrentUser();
    UserDTO getCurrentUserDTO();
}
