package org.leonardo.music_streaming_management.service.music;

import org.leonardo.music_streaming_management.dto.music.MusicCreateRequestDTO;
import org.leonardo.music_streaming_management.dto.music.MusicCreatedResponseDTO;

public interface IMusicService {
    MusicCreatedResponseDTO createMusic(MusicCreateRequestDTO musicCreateRequestDTO);
}
