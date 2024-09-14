package org.leonardo.music_streaming_management.controller.album;

import org.leonardo.music_streaming_management.dto.album.*;
import org.leonardo.music_streaming_management.service.album.IAlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final IAlbumService albumService;

    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/create")
    public ResponseEntity<AlbumCreatedResponseDTO> createAlbum(@RequestBody AlbumRequestDTO albumRequestDTO){
        AlbumCreatedResponseDTO albumCreatedResponseDTO = albumService.createAlbum(albumRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(albumCreatedResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AlbumUpdateResponseDTO> updateAlbum(@PathVariable Long id, @RequestBody AlbumRequestDTO albumRequestDTO){
        AlbumUpdateResponseDTO albumUpdateResponseDTO = albumService.updateAlbum(id, albumRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(albumUpdateResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumGetResponseDTO> getAlbumById(@PathVariable Long id){
        AlbumGetResponseDTO albumGetResponseDTO = albumService.getAlbumById(id);
        return ResponseEntity.status(HttpStatus.OK).body(albumGetResponseDTO);
    }

    @GetMapping
    public ResponseEntity<AlbumListResponseDTO> getAllAlbums(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        AlbumListResponseDTO albumListResponseDTO = albumService.getAllAlbum(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(albumListResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlbumDeleteResponseDTO> deleteAlbum(@PathVariable Long id){
        AlbumDeleteResponseDTO albumDeleteResponseDTO = albumService.deleteAlbumById(id);
        return ResponseEntity.status(HttpStatus.OK).body(albumDeleteResponseDTO);
    }
}
