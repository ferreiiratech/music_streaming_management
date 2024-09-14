package org.leonardo.music_streaming_management.dto.album;

public record AlbumCreatedResponseDTO(
        boolean success,
        String message,
        AlbumDataDTO data
) {
}
