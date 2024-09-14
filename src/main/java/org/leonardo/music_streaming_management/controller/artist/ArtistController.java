package org.leonardo.music_streaming_management.controller.artist;

import org.leonardo.music_streaming_management.dto.artist.*;
import org.leonardo.music_streaming_management.service.artist.IArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    private final IArtistService artistService;

    public ArtistController(IArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/create")
    public ResponseEntity<ArtistCreatedResponseDTO> createArtist(@RequestBody ArtistRequestDTO artistRequestDTO) {
        ArtistCreatedResponseDTO artistCreatedResponseDTO = artistService.createArtist(artistRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(artistCreatedResponseDTO);
    }
}
