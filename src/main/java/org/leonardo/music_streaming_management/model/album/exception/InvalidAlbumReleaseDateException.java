package org.leonardo.music_streaming_management.model.album.exception;

public class InvalidAlbumReleaseDateException extends RuntimeException {
    public InvalidAlbumReleaseDateException(String message) {
        super(message);
    }
}
