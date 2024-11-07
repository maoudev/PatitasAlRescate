package tech.maou.patitasalrescate.persistence.entity.DTO;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DogDTO {
    private UUID id;
    private String title;
    private String description;
    private int age;
    private String sex;
    private boolean vaccinated;
    private boolean sterilized;
    private boolean adopted;
    private UserDTO user;
    private Set<DogImageDTO> images;
}
