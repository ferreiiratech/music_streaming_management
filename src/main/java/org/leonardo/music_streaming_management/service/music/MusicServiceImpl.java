package org.leonardo.music_streaming_management.service.music;

import org.leonardo.music_streaming_management.repository.music.MusicRepository;
import org.springframework.stereotype.Service;

@Service
public class MusicServiceImpl implements IMusicService {
    private final MusicRepository musicRepository;

    public MusicServiceImpl(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

}
