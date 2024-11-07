package tech.maou.patitasalrescate.exception;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateFieldException extends RuntimeException {
    private String code;
    public DuplicateFieldException(String message, String code) {
        super(message);
        this.code = code;
    }
}
