package tech.maou.patitasalrescate.exception;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadBlobException extends RuntimeException {
    private String code;
    public UploadBlobException(String message, String code) {
        super(message);
        this.code = code;
    }
}
