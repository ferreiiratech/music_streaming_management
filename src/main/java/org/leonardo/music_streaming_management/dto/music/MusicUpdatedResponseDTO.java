package org.leonardo.music_streaming_management.dto.music;

public record MusicUpdatedResponseDTO(
        boolean success,
        String message,
        MusicDataDTO data
) {
}
