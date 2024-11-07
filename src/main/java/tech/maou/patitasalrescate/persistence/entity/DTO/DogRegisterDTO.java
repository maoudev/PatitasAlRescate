package tech.maou.patitasalrescate.persistence.entity.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DogRegisterDTO {
    private String title;
    private String description;
    private int age;
    private String sex;
    private boolean vaccinated;
    private boolean sterilized;
    private boolean adopted;
    private MultipartFile[] images;
}
