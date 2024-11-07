package tech.maou.patitasalrescate.persistence.entity.DTO;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDTO {
    private String message;
    private String code;
}
