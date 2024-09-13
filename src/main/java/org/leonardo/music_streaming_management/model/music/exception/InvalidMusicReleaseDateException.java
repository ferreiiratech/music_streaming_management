package org.leonardo.music_streaming_management.model.music.exception;

public class InvalidMusicReleaseDateException extends RuntimeException {
    public InvalidMusicReleaseDateException(String message) {
        super(message);
    }
}
