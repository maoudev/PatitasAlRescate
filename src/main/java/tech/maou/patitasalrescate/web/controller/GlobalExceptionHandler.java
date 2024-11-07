package tech.maou.patitasalrescate.web.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.maou.patitasalrescate.exception.*;
import tech.maou.patitasalrescate.persistence.entity.DTO.ExceptionDTO;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ExceptionDTO> handleDuplicateFieldException(DuplicateFieldException e) {
        logger.error("Duplicate field exception", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message(e.getMessage()).code(e.getCode()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errDto);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ExceptionDTO> handleInvalidFieldException(InvalidFieldException e) {
        logger.error("Invalid Field Exception", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message(e.getMessage()).code(e.getCode()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errDto);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDTO> handleAuthenticationException(AuthenticationException e) {
        logger.error("Authentication Exception", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message("Unauthorized").code("err-10").build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errDto);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleUserNotFoundException(UserNotFoundException e) {
        logger.error("User not found", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message(e.getMessage()).code(e.getCode()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errDto);
    }

    @ExceptionHandler(UploadBlobException.class)
    public ResponseEntity<ExceptionDTO> handleUploadBlobException(UploadBlobException e) {
        logger.error("Upload Blob Exception", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message(e.getMessage()).code(e.getCode()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errDto);
    }

    @ExceptionHandler(InvalidDogUUID.class)
    public ResponseEntity<ExceptionDTO> handleInvalidDogUUID(InvalidDogUUID e) {
        logger.error("Invalid Dog UUID", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message(e.getMessage()).code(e.getCode()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errDto);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ExceptionDTO> handleUserNotAuthenticatedException(UserNotAuthenticatedException e) {
        logger.error("User not authenticated", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message(e.getMessage()).code(e.getCode()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDTO> handleBadCredentialsException(BadCredentialsException e) {
        logger.error("Bad credentials", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message("Bad credentials").code("err-21").build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errDto);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionDTO> handleSignatureException(SignatureException e) {
        logger.error("Signature exception", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message("Invalid token").code("err-22").build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errDto);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionDTO> handleExpiredJwtException(ExpiredJwtException e) {
        logger.error("Expired token", e);
        ExceptionDTO errDto = ExceptionDTO.builder().message("Expired token").code("err-23").build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errDto);
    }


}
