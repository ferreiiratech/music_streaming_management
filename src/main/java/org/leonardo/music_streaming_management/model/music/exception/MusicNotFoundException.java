package org.leonardo.music_streaming_management.model.music.exception;

public class MusicNotFoundException extends RuntimeException {
    public MusicNotFoundException(String message) {
        super(message);
    }
}
