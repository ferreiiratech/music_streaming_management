package org.leonardo.music_streaming_management.dto.artist;

public record ArtistGetResponseDTO(
        boolean success,
        String message,
        ArtistDataDTO data
) {
}
