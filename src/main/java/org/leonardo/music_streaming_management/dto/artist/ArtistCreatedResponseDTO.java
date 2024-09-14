package org.leonardo.music_streaming_management.dto.artist;

public record ArtistCreatedResponseDTO(
        boolean success,
        String message,
        ArtistDataDTO data
) {
}
