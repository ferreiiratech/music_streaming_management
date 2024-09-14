package org.leonardo.music_streaming_management.dto.album;

public record AlbumGetResponseDTO(
        boolean success,
        String message,
        AlbumDataDTO data
) {
}
