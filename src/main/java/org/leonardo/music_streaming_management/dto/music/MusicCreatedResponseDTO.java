package org.leonardo.music_streaming_management.dto.music;

public record MusicCreatedResponseDTO(
        boolean status,
        String message,
        MusicDataDTO data
) {
}
