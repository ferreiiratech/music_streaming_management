package org.leonardo.music_streaming_management.dto.music;

import java.util.List;

public record SearchMusicResponseDTO(
        boolean success,
        String message,
        List<MusicDataDTO> data
) {
}
