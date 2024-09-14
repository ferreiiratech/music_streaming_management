package org.leonardo.music_streaming_management.service.artist;

import org.leonardo.music_streaming_management.config.exception.AccessDatabaseFailureException;
import org.leonardo.music_streaming_management.dto.artist.*;
import org.leonardo.music_streaming_management.model.artist.ArtistEntity;
import org.leonardo.music_streaming_management.repository.artist.IArtistRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements IArtistService {
    private final IArtistRepository artistRepository;

    public ArtistServiceImpl(IArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public ArtistCreatedResponseDTO createArtist(ArtistRequestDTO artistRequestDTO){
        try {
            validateDataArtist(artistRequestDTO);

            Optional<ArtistEntity> artistFound = artistRepository.findByName(artistRequestDTO.name());

            if(artistFound.isPresent()){
                throw new IllegalArgumentException("Artist already exists");
            }

            ArtistEntity artistEntity = new ArtistEntity(artistRequestDTO.name(), artistRequestDTO.nationality());
            ArtistEntity artistCreated = artistRepository.save(artistEntity);

            return new ArtistCreatedResponseDTO(
                    true,
                    "Artist created successfully",
                    new ArtistDataDTO(artistCreated.getName(), artistCreated.getNationality())
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private ArtistEntity getExistingArtist(Long artistId){
        try {
            Optional<ArtistEntity> artistFound = artistRepository.findById(artistId);

            if(artistFound.isEmpty()){
                throw new IllegalArgumentException("Artist not found");
            }

            return artistFound.get();
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private void validateDataArtist(ArtistRequestDTO artistRequestDTO){
        if(artistRequestDTO.name() == null || artistRequestDTO.name().isEmpty()){
            throw new IllegalArgumentException("Artist name cannot be empty");
        }

        if(artistRequestDTO.nationality() == null || artistRequestDTO.nationality().isEmpty()){
            throw new IllegalArgumentException("Nationality cannot be empty");
        }
    }
}
