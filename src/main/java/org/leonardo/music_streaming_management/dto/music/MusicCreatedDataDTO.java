package org.leonardo.music_streaming_management.dto.music;

import java.time.LocalDate;

public record MusicCreatedDataDTO(
        String title,
        String genre,
        int duration,
        LocalDate releaseDate,
        String artist,
        String album
) {
}
