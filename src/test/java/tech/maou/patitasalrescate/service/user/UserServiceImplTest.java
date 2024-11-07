package tech.maou.patitasalrescate.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.maou.patitasalrescate.exception.DuplicateFieldException;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserRegistrationDTO;
import tech.maou.patitasalrescate.persistence.entity.RoleEntity;
import tech.maou.patitasalrescate.persistence.entity.RoleEnum;
import tech.maou.patitasalrescate.persistence.entity.UserEntity;
import tech.maou.patitasalrescate.persistence.repository.RoleRepository;
import tech.maou.patitasalrescate.persistence.repository.UserRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUserSuccessfully() {
        RoleEntity roleEntity = RoleEntity.builder()
                .roleEnum(RoleEnum.USER)
                .build();

        Mockito.when(roleRepository.findByRoleEnum(RoleEnum.USER)).thenReturn(roleEntity);

        UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder()
                .username("newUser")
                .name("newName")
                .lastName("newLastName")
                .city("Rancagua")
                .phone(944335522)
                .email("newuser@gmail.com")
                .password("password123")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .username("newUser")
                .name("newName")
                .lastname("newLastName")
                .city("Rancagua")
                .phone(944335522)
                .email("newuser@gmail.com")
                .password("encodedPassword")
                .roles(Set.of(roleEntity))
                .build();

        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);

        userService.save(userRegistrationDTO);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    void saveUserWithDuplicateUsername() {
        UserEntity existingUser = UserEntity.builder()
                .username("existingUser")
                .build();

        Mockito.when(userRepository.findByUsername("existingUser")).thenReturn(java.util.Optional.of(existingUser));

        UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder()
                .username("existingUser")
                .build();

        assertThrows(DuplicateFieldException.class, () -> userService.save(userRegistrationDTO));
    }

    @Test
    void saveUserWithDuplicateEmail() {
        UserEntity existingUser = UserEntity.builder()
                .email("existing@gmail.com")
                .build();

        Mockito.when(userRepository.findByEmail("existing@gmail.com")).thenReturn(java.util.Optional.of(existingUser));

        UserRegistrationDTO userRegistrationDTO = UserRegistrationDTO.builder()
                .email("existing@gmail.com")
                .build();

        assertThrows(DuplicateFieldException.class, () -> userService.save(userRegistrationDTO));
    }


}