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

    @Override
    public ArtistUpdateResponseDTO updateArtist(Long artistId, ArtistRequestDTO artistRequestDTO){
        try {
            validateDataArtist(artistRequestDTO);

            Optional<ArtistEntity> artistFound = artistRepository.findByName(artistRequestDTO.name());

            if(artistFound.isPresent()){
                throw new IllegalArgumentException("Artist name already exists");
            }

            ArtistEntity artistExisting = getExistingArtist(artistId);
            BeanUtils.copyProperties(artistRequestDTO, artistExisting);

            ArtistEntity artistUpdated = artistRepository.save(artistExisting);

            return new ArtistUpdateResponseDTO(
                    true,
                    "Artist updated successfully",
                    new ArtistDataDTO(artistUpdated.getName(), artistUpdated.getNationality())
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public ArtistGetResponseDTO getArtistById(Long artistId){
        try {
            Optional<ArtistEntity> artistEntity = artistRepository.findById(artistId);

            if (artistEntity.isEmpty()) {
                throw new IllegalArgumentException("Artist not found");
            }

            return new ArtistGetResponseDTO(
                    true,
                    "Artist founded successfully",
                    new ArtistDataDTO(artistEntity.get().getName(), artistEntity.get().getNationality())
            );

        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public ArtistListResponseDTO getAllArtist(int page, int size) {
        try {
            Page<ArtistEntity> artistEntityPage = artistRepository.findAll(PageRequest.of(page, size));

            List<ArtistEntity> artistEntityList = artistEntityPage.getContent();

            List<ArtistDataDTO> artistDataDTOList = new ArrayList<>();
            artistEntityList.forEach(artistEntity -> artistDataDTOList.add(
                    new ArtistDataDTO(artistEntity.getName(), artistEntity.getNationality()))
            );

            return new ArtistListResponseDTO(
                    true,
                    "List of artist founded successfully",
                    artistDataDTOList
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
