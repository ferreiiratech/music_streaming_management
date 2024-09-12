package org.leonardo.music_streaming_management.dto.music;


import java.time.LocalDate;

public record MusicCreateRequestDTO(
        String title,
        String gente,
        int duration,
        LocalDate releaseDate,
        String artist,
        String album
) {
}
