package tech.maou.patitasalrescate.exception;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvalidFieldException extends RuntimeException {
    private String code;

    public InvalidFieldException(String message, String code) {
      super(message);
      this.code = code;
    }
}
