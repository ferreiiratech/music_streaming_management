package org.leonardo.music_streaming_management.dto.music;

import java.time.LocalDateTime;

public record MusicDataDTO(
        String title,
        String genre,
        int duration,
        LocalDateTime releaseDate,
        String artist,
        String album
) {
}
