package org.leonardo.music_streaming_management.service.music;

import org.leonardo.music_streaming_management.dto.music.*;

public interface IMusicService {
    MusicCreatedResponseDTO createMusic(MusicRequestDTO musicRequestDTO);
    MusicUpdatedResponseDTO updateMusic(Long musicId, MusicRequestDTO musicRequestDTO);
    MusicGetResponseDTO getMusicById(Long musicId);
    MusicDeleteResponseDTO deleteMusic(Long musicId);
}
