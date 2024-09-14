package org.leonardo.music_streaming_management.service.music;

import org.leonardo.music_streaming_management.dto.music.MusicRequestDTO;
import org.leonardo.music_streaming_management.dto.music.MusicCreatedResponseDTO;
import org.leonardo.music_streaming_management.dto.music.MusicUpdatedResponseDTO;

public interface IMusicService {
    MusicCreatedResponseDTO createMusic(MusicRequestDTO musicRequestDTO);
    MusicUpdatedResponseDTO updateMusic(Long musicId, MusicRequestDTO musicRequestDTO);
}
