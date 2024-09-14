package org.leonardo.music_streaming_management.service.music;

import org.leonardo.music_streaming_management.dto.music.*;

import java.util.Optional;

public interface IMusicService {
    MusicCreatedResponseDTO createMusic(MusicRequestDTO musicRequestDTO);
    MusicUpdatedResponseDTO updateMusic(Long musicId, MusicRequestDTO musicRequestDTO);
    MusicGetResponseDTO getMusicById(Long musicId);
    MusicDeleteResponseDTO deleteMusic(Long musicId);
    SearchMusicResponseDTO searchMusics(
            Optional<String> musicTitle,
            Optional<String> musicGenre,
            Optional<String> artistName,
            Optional<String> albumTitle,
            int page,
            int size
    );
}
