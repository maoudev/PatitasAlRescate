package tech.maou.patitasalrescate.exception;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNotAuthenticatedException extends RuntimeException {
  private String code;
  public UserNotAuthenticatedException(String message, String code) {
    super(message);
    this.code = code;
  }
}
