package org.leonardo.music_streaming_management.dto.music;

public record MusicGetResponseDTO(
        boolean status,
        String message,
        MusicDataDTO data
) {
}
