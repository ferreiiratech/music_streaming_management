package org.leonardo.music_streaming_management.dto.exception;

public record ExceptionResponseDTO (
        boolean success,
        String message
){
}
