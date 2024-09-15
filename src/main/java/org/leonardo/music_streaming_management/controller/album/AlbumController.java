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

    @PatchMapping("/{albumId}")
    public ResponseEntity<AlbumUpdateResponseDTO> updateAlbum(
            @PathVariable Long albumId,
            @RequestBody AlbumRequestDTO albumRequestDTO
    ){
        AlbumUpdateResponseDTO albumUpdateResponseDTO = albumService.updateAlbum(albumId, albumRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(albumUpdateResponseDTO);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumGetResponseDTO> getAlbumById(@PathVariable Long albumId){
        AlbumGetResponseDTO albumGetResponseDTO = albumService.getAlbumById(albumId);
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

    @DeleteMapping("/{albumId}")
    public ResponseEntity<AlbumDeleteResponseDTO> deleteAlbum(@PathVariable Long albumId){
        AlbumDeleteResponseDTO albumDeleteResponseDTO = albumService.deleteAlbumById(albumId);
        return ResponseEntity.status(HttpStatus.OK).body(albumDeleteResponseDTO);
    }
}
