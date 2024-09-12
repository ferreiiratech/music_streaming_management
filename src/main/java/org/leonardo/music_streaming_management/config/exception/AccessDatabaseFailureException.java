package org.leonardo.music_streaming_management.config.exception;

public class AccessDatabaseFailureException extends RuntimeException {
    public AccessDatabaseFailureException(String message) {
        super(message);
    }
}
