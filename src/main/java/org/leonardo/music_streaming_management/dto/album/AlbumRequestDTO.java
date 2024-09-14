package org.leonardo.music_streaming_management.dto.album;

import java.time.LocalDateTime;

public record AlbumRequestDTO(
        String title,
        LocalDateTime releaseDate,
        String artistName
) {
}
