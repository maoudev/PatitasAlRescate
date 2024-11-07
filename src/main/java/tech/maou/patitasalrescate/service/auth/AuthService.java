package tech.maou.patitasalrescate.service.auth;

import tech.maou.patitasalrescate.exception.UserNotFoundException;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserCredentialsDTO;

public interface AuthService {
    String login(UserCredentialsDTO credentials) throws UserNotFoundException;
}
