package org.leonardo.music_streaming_management.service.album;

import org.leonardo.music_streaming_management.dto.album.*;

public interface IAlbumService {
    AlbumCreatedResponseDTO createAlbum(AlbumRequestDTO albumRequestDTO);
    AlbumUpdateResponseDTO updateAlbum(Long albumId, AlbumRequestDTO albumRequestDTO);
    AlbumGetResponseDTO getAlbumById(Long albumId);
    AlbumListResponseDTO getAllAlbum(int page, int size);
    AlbumDeleteResponseDTO deleteAlbumById(Long albumId);
}
