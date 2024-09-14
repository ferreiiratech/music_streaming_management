package org.leonardo.music_streaming_management.controller.music;

import org.leonardo.music_streaming_management.dto.music.MusicGetResponseDTO;
import org.leonardo.music_streaming_management.dto.music.MusicRequestDTO;
import org.leonardo.music_streaming_management.dto.music.MusicCreatedResponseDTO;
import org.leonardo.music_streaming_management.dto.music.MusicUpdatedResponseDTO;
import org.leonardo.music_streaming_management.service.music.IMusicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music")
public class MusicController {
    private final IMusicService musicService;

    public MusicController(IMusicService musicService) {
        this.musicService = musicService;
    }

    @PostMapping("/create")
    public ResponseEntity<MusicCreatedResponseDTO> createMusic(@RequestBody MusicRequestDTO musicRequestDTO) {
        MusicCreatedResponseDTO musicCreatedResponseDTO = musicService.createMusic(musicRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(musicCreatedResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MusicUpdatedResponseDTO> updateMusic(@PathVariable Long id, @RequestBody MusicRequestDTO musicRequestDTO) {
        MusicUpdatedResponseDTO musicUpdatedResponseDTO = musicService.updateMusic(id, musicRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(musicUpdatedResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicGetResponseDTO> getMusicById(@PathVariable Long id) {
        MusicGetResponseDTO musicGetResponseDTO = musicService.getMusicById(id);
        return ResponseEntity.status(HttpStatus.OK).body(musicGetResponseDTO);
    }
}
