package org.leonardo.music_streaming_management.dto.album;

import java.time.LocalDateTime;

public record AlbumDataDTO(
        String title,
        LocalDateTime releaseDate,
        String artistName
) {
}
