package tech.maou.patitasalrescate.persistence.entity.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
}
