package org.leonardo.music_streaming_management.controller.music;

import org.leonardo.music_streaming_management.service.music.IMusicService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {
    private final IMusicService musicService;

    public MusicController(IMusicService musicService) {
        this.musicService = musicService;
    }
}
