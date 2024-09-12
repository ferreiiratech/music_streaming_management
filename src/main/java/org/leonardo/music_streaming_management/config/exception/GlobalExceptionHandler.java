package org.leonardo.music_streaming_management.config.exception;

import org.leonardo.music_streaming_management.dto.exception.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDatabaseFailureException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAccessDatabaseFailureException(AccessDatabaseFailureException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }
}
