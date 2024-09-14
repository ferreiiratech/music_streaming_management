package org.leonardo.music_streaming_management.dto.album;

import java.util.List;

public record AlbumListResponseDTO(
        boolean success,
        String message,
        List<AlbumDataDTO> data
) {
}
