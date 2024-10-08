package org.leonardo.music_streaming_management.service.artist;

import org.leonardo.music_streaming_management.dto.artist.*;

public interface IArtistService {
    ArtistCreatedResponseDTO createArtist(ArtistRequestDTO artistRequestDTO);
    ArtistUpdateResponseDTO updateArtist(Long artistId, ArtistRequestDTO artistRequestDTO);
    ArtistGetResponseDTO getArtistById(Long artistId);
    ArtistListResponseDTO getAllArtist(int page, int size);
    ArtistDeleteResponseDTO deleteArtistById(Long artistId);
}
