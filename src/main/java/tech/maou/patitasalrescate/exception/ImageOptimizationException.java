package tech.maou.patitasalrescate.exception;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageOptimizationException extends RuntimeException {
    private String code;
    public ImageOptimizationException(String message, String code) {
      super(message);
      this.code = code;
    }
}
