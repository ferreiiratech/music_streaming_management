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

    @PatchMapping("/{id}")
    public ResponseEntity<ArtistUpdateResponseDTO> updateArtist(@PathVariable Long id, @RequestBody ArtistRequestDTO artistRequestDTO) {
        ArtistUpdateResponseDTO artistUpdateResponseDTO = artistService.updateArtist(id, artistRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(artistUpdateResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistGetResponseDTO> getArtistById(@PathVariable Long id) {
        ArtistGetResponseDTO artistGetResponseDTO = artistService.getArtistById(id);
        return ResponseEntity.status(HttpStatus.OK).body(artistGetResponseDTO);
    }

    @GetMapping
    public ResponseEntity<ArtistListResponseDTO> getAllArtists(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        ArtistListResponseDTO artistListResponseDTO = artistService.getAllArtist(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(artistListResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArtistDeleteResponseDTO> deleteArtistById(@PathVariable Long id) {
        ArtistDeleteResponseDTO artistDeleteResponseDTO = artistService.deleteArtistById(id);
        return ResponseEntity.status(HttpStatus.OK).body(artistDeleteResponseDTO);
    }
}
