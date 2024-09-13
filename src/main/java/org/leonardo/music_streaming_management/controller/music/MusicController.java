package org.leonardo.music_streaming_management.controller.music;

import org.leonardo.music_streaming_management.dto.music.MusicCreateRequestDTO;
import org.leonardo.music_streaming_management.dto.music.MusicCreatedResponseDTO;
import org.leonardo.music_streaming_management.service.music.IMusicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/music")
public class MusicController {
    private final IMusicService musicService;

    public MusicController(IMusicService musicService) {
        this.musicService = musicService;
    }

    @PostMapping("/create")
    public ResponseEntity<MusicCreatedResponseDTO> createMusic(@RequestBody MusicCreateRequestDTO musicCreateRequestDTO) {
        MusicCreatedResponseDTO musicCreatedResponseDTO = musicService.createMusic(musicCreateRequestDTO);
        return ResponseEntity.ok(musicCreatedResponseDTO);
    }
}
