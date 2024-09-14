package org.leonardo.music_streaming_management.controller.album;

import org.leonardo.music_streaming_management.repository.album.IAlbumRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final IAlbumRepository albumRepository;

    public AlbumController(IAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
}
