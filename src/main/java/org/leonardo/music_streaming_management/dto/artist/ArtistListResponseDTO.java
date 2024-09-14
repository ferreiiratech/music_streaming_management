package org.leonardo.music_streaming_management.dto.artist;

import java.util.List;

public record ArtistListResponseDTO (
        boolean success,
        String message,
        List<ArtistDataDTO> data
) {
}
