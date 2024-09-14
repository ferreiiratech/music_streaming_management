package org.leonardo.music_streaming_management.dto.artist;

public record ArtistUpdateResponseDTO(
        boolean success,
        String message,
        ArtistDataDTO data
) {
}
