package org.leonardo.music_streaming_management.config.exception;

import org.leonardo.music_streaming_management.dto.exception.ExceptionResponseDTO;
import org.leonardo.music_streaming_management.model.album.exception.AlbumNotFoundException;
import org.leonardo.music_streaming_management.model.album.exception.InvalidAlbumReleaseDateException;
import org.leonardo.music_streaming_management.model.album.exception.InvalidAlbumTitleException;
import org.leonardo.music_streaming_management.model.artist.exception.ArtistNotFoundException;
import org.leonardo.music_streaming_management.model.artist.exception.InvalidArtistNameException;
import org.leonardo.music_streaming_management.model.artist.exception.InvalidArtistNationalityException;
import org.leonardo.music_streaming_management.model.music.exception.*;
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

    @ExceptionHandler(InvalidMusicAlbumException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidMusicAlbumException(InvalidMusicAlbumException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidMusicArtistException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidMusicArtistException(InvalidMusicArtistException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidMusicDurationException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidMusicDurationException(InvalidMusicDurationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidMusicGenreException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidMusicGenreException(InvalidMusicGenreException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidMusicReleaseDateException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidMusicReleaseDateException(InvalidMusicReleaseDateException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidMusicTitleException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidMusicTitleException(InvalidMusicTitleException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(MusicNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMusicNotFoundException(MusicNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleArtistNotFoundException(ArtistNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidArtistNameException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidArtistNameException(InvalidArtistNameException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidArtistNationalityException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidArtistNationalityException(InvalidArtistNationalityException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAlbumNotFoundException(AlbumNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidAlbumReleaseDateException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidAlbumReleaseDateException(InvalidAlbumReleaseDateException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }

    @ExceptionHandler(InvalidAlbumTitleException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidAlbumTitleException(InvalidAlbumTitleException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseDTO(false, exception.getMessage()));
    }
}
