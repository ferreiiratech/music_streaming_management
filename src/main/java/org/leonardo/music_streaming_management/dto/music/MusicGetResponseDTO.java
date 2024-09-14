package org.leonardo.music_streaming_management.dto.music;

public record MusicGetResponseDTO(
        boolean success,
        String message,
        MusicDataDTO data
) {
}
