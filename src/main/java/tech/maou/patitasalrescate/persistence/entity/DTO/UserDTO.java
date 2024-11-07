package tech.maou.patitasalrescate.persistence.entity.DTO;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String name;
    private String lastName;
    private String city;
    private int phone;
    private String email;
    private Set<RoleDTO> roles;
}
