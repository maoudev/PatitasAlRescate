package tech.maou.patitasalrescate.exception;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DogNotFound extends RuntimeException {
    private String code;
    public DogNotFound(String message, String code) {
      super(message);
      this.code = code;
    }
}
