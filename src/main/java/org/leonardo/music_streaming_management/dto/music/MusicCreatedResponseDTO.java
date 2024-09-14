package org.leonardo.music_streaming_management.dto.music;

public record MusicCreatedResponseDTO(
        boolean success,
        String message,
        MusicDataDTO data
) {
}
