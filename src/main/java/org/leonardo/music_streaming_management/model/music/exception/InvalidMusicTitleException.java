package org.leonardo.music_streaming_management.model.music.exception;

public class InvalidMusicTitleException extends RuntimeException {
    public InvalidMusicTitleException(String message) {
        super(message);
    }
}
