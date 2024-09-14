package org.leonardo.music_streaming_management.dto.album;

public record AlbumUpdateResponseDTO(
        boolean success,
        String message,
        AlbumDataDTO data
) {
}
