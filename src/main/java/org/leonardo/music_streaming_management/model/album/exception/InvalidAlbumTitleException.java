package org.leonardo.music_streaming_management.model.album.exception;

public class InvalidAlbumTitleException extends RuntimeException {
    public InvalidAlbumTitleException(String message) {
        super(message);
    }
}
