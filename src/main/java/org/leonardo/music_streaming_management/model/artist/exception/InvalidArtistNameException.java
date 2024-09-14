package org.leonardo.music_streaming_management.model.artist.exception;

public class InvalidArtistNameException extends RuntimeException {
    public InvalidArtistNameException(String message) {
        super(message);
    }
}
