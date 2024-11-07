package tech.maou.patitasalrescate.exception;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvalidDogUUID extends RuntimeException {
    private String code;
    public InvalidDogUUID(String message, String code) {
      super(message);
        this.code = code;
    }
}
