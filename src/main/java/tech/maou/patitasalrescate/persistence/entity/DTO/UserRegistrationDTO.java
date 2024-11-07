package tech.maou.patitasalrescate.persistence.entity.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDTO {
    private String username;
    private String name;
    private String lastName;
    private String city;
    private int phone;
    private String email;
    private String password;
}
