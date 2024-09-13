package org.leonardo.music_streaming_management.model.music.exception;

public class InvalidMusicDurationException extends RuntimeException {
    public InvalidMusicDurationException(String message) {
        super(message);
    }
}
