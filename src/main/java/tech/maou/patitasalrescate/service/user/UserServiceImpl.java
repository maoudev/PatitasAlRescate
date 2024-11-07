package tech.maou.patitasalrescate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.maou.patitasalrescate.exception.DuplicateFieldException;
import tech.maou.patitasalrescate.exception.InvalidFieldException;
import tech.maou.patitasalrescate.exception.UserNotAuthenticatedException;
import tech.maou.patitasalrescate.exception.UserNotFoundException;
import tech.maou.patitasalrescate.persistence.entity.DTO.RoleDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserDTO;
import tech.maou.patitasalrescate.persistence.entity.DTO.UserRegistrationDTO;
import tech.maou.patitasalrescate.persistence.entity.RoleEntity;
import tech.maou.patitasalrescate.persistence.entity.RoleEnum;
import tech.maou.patitasalrescate.persistence.entity.UserEntity;
import tech.maou.patitasalrescate.persistence.repository.RoleRepository;
import tech.maou.patitasalrescate.persistence.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(UserRegistrationDTO userDTO) throws DuplicateFieldException {
        UserEntity userEntity = this.userRepository.findByUsername(userDTO.getUsername()).orElse(null);
        if (userEntity != null) {
            throw new DuplicateFieldException("Nombre de usuario ya registrado", "err-01");
        }

        userEntity = this.userRepository.findByEmail(userDTO.getEmail()).orElse(null);
        if (userEntity != null) {
            throw new DuplicateFieldException("Email ya registrado", "err-02");
        }

        this.validateUser(userDTO);

        RoleEntity userRole = this.roleRepository.findByRoleEnum(RoleEnum.USER);

        if (userRole == null) {
            throw new IllegalStateException("Role not found: USER");
        }

        this.userRepository.save(UserEntity.builder()
                .username(userDTO.getUsername())
                .name(userDTO.getName())
                .lastname(userDTO.getLastName())
                .city(userDTO.getCity())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .password(this.passwordEncoder.encode(userDTO.getPassword()))
                .roles(Set.of(userRole))
                .build()
        );
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public UserDTO findByUsername(String username) throws UserNotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado", "err-11"));

        return this.convertUserEntityToDTO(userEntity);
    }

    @Override
    public UserDTO findByEmail(String email) throws UserNotFoundException {
        this.validateEmail(email);

        UserEntity userEntity = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado", "err-11"));

        return convertUserEntityToDTO(userEntity);

    }

    @Override
    public UserEntity findUserEntityByUsername(String username) throws UserNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado", "err-11"));
    }


    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            String username = ((User) authentication.getPrincipal()).getUsername();
            return this.userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado", "err-11"));
        } else {
            throw new UserNotAuthenticatedException("Usuario no autenticado", "err-20");
        }
    }

    @Override
    public UserDTO getCurrentUserDTO() {
        UserEntity userEntity = this.getCurrentUser();
        return this.convertUserEntityToDTO(userEntity);
    }


    private void validateUser(UserRegistrationDTO userDTO) throws InvalidFieldException {
        Set<String> comunas = new HashSet<>(Set.of(
                "Rancagua", "Graneros", "Codegua", "Coinco", "Coltauco",
                "Doñihue", "Machalí", "Malloa", "Mostazal", "Olivar",
                "Peumo", "Pichidegua", "Quinta de Tilcoco", "Rengo",
                "Requínoa", "San Vicente", "San Fernando", "Chépica",
                "Chimbarongo", "Lolol", "Nancagua", "Palmilla",
                "Peralillo", "Placilla", "Pumanque", "Santa Cruz",
                "Pichilemu", "La Estrella", "Litueche", "Marchigüe",
                "Navidad", "Paredones"
        ));

        if (userDTO.getUsername().length() > 50) {
            throw new InvalidFieldException("El nombre de usuario es demasiado largo", "err-03");
        }

        if (userDTO.getName().length() > 50) {
            throw new InvalidFieldException("El nombre es demasiado largo", "err-07");
        }

        if (userDTO.getLastName().length() > 50) {
            throw new InvalidFieldException("el apellido es demasiado largo", "err-08");
        }

        if (!comunas.contains(userDTO.getCity())) {
            throw new InvalidFieldException("La comuna es inválida", "err-05");
        }

        String phone = String.valueOf(userDTO.getPhone());
        if (!phone.matches("^9\\d{8}$")) {
            throw new InvalidFieldException("El número de teléfono es inválido", "err-09");
        }

        if (!userDTO.getEmail().matches("[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}")) {
            throw new InvalidFieldException("El email es inválido", "err-04");
        }

        if (userDTO.getPassword().length() < 8) {
            throw new InvalidFieldException("La contraseña es demasiado corta", "err-06");
        }
    }

    private void validateEmail(String email) throws InvalidFieldException {
        if (!email.matches("[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}")) {
            throw new InvalidFieldException("El email es inválido", "err-04");
        }
    }

    private UserDTO convertUserEntityToDTO(UserEntity userEntity) {
        Set<RoleDTO> rolesDTO = new HashSet<>();

        userEntity.getRoles().stream()
                .map(this::convertRoleEntityToDTO)
                .forEach(rolesDTO::add);

        return UserDTO.builder()
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .lastName(userEntity.getLastname())
                .city(userEntity.getCity())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .roles(rolesDTO)
                .build();
    }

    private RoleDTO convertRoleEntityToDTO(RoleEntity roleEntity) {
        return RoleDTO.builder()
                .name(roleEntity.getRoleEnum().name())
                .build();
    }
}
