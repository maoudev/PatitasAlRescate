package tech.maou.patitasalrescate.exception;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {
  private String code;
  public UserNotFoundException(String message, String code) {
    super(message);
    this.code = code;
    }
}
