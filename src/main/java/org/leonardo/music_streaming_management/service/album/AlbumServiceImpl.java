package org.leonardo.music_streaming_management.service.album;

import org.leonardo.music_streaming_management.config.exception.AccessDatabaseFailureException;
import org.leonardo.music_streaming_management.dto.album.*;
import org.leonardo.music_streaming_management.model.album.AlbumEntity;
import org.leonardo.music_streaming_management.model.album.exception.AlbumNotFoundException;
import org.leonardo.music_streaming_management.model.album.exception.InvalidAlbumReleaseDateException;
import org.leonardo.music_streaming_management.model.album.exception.InvalidAlbumTitleException;
import org.leonardo.music_streaming_management.model.artist.ArtistEntity;
import org.leonardo.music_streaming_management.model.artist.exception.ArtistNotFoundException;
import org.leonardo.music_streaming_management.model.artist.exception.InvalidArtistNameException;
import org.leonardo.music_streaming_management.repository.album.IAlbumRepository;
import org.leonardo.music_streaming_management.repository.artist.IArtistRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements IAlbumService {
    private IAlbumRepository albumRepository;
    private IArtistRepository artistRepository;

    public AlbumServiceImpl(IAlbumRepository albumRepository, IArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public AlbumCreatedResponseDTO createAlbum(AlbumRequestDTO albumRequestDTO) {
        try {
            validateDateAlbum(albumRequestDTO);

            Optional<AlbumEntity> albumFound = albumRepository.findByTitle(albumRequestDTO.title());

            if (albumFound.isPresent()) {
                throw new InvalidAlbumTitleException("Album already exists");
            }

            Optional<ArtistEntity> artistFound = artistRepository.findByName(albumRequestDTO.artistName());

            if(artistFound.isEmpty()){
                throw new ArtistNotFoundException("Artist not found");
            }

            AlbumEntity albumEntity = new AlbumEntity();
            albumEntity.setTitle(albumRequestDTO.title());
            albumEntity.setReleaseDate(albumRequestDTO.releaseDate());
            albumEntity.setArtist(artistFound.get());

            AlbumEntity albumCreated = albumRepository.save(albumEntity);

            return new AlbumCreatedResponseDTO(
                    true,
                    "Album created successfully",
                    new AlbumDataDTO(
                            albumCreated.getTitle(),
                            albumCreated.getReleaseDate(),
                            albumCreated.getArtist().getName()
                    )
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public AlbumUpdateResponseDTO updateAlbum(Long albumId, AlbumRequestDTO albumRequestDTO) {
        try {
            validateDateAlbum(albumRequestDTO);

            Optional<AlbumEntity> albumFound = albumRepository.findById(albumId);

            if (albumFound.isEmpty()) {
                throw new AlbumNotFoundException("Album not found");
            }

            Optional<AlbumEntity> albumExistingWithTitle = albumRepository.findByTitle(albumRequestDTO.title());

            if(albumExistingWithTitle.isPresent()){
                throw new InvalidAlbumTitleException("Album title already exists");
            }

            Optional<ArtistEntity> artistFound = artistRepository.findByName(albumRequestDTO.artistName());

            if(artistFound.isEmpty()){
                throw new ArtistNotFoundException("Artist not found");
            }

            AlbumEntity albumEntity = new AlbumEntity();
            BeanUtils.copyProperties(albumRequestDTO, albumEntity);
            albumEntity.setArtist(artistFound.get());

            AlbumEntity albumCreate = albumRepository.save(albumEntity);

            return new AlbumUpdateResponseDTO(
                    true,
                    "Album updated successufully",
                    new AlbumDataDTO(
                        albumCreate.getTitle(),
                        albumCreate.getReleaseDate(),
                        albumCreate.getArtist().getName()
                    )
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public AlbumGetResponseDTO getAlbumById(Long albumId) {
        try {
            Optional<AlbumEntity> albumFound = albumRepository.findById(albumId);

            if (albumFound.isEmpty()) {
                throw new AlbumNotFoundException("Album not found");
            }

            return new AlbumGetResponseDTO(
                    true,
                    "Album found successfully",
                    new AlbumDataDTO(
                            albumFound.get().getTitle(),
                            albumFound.get().getReleaseDate(),
                            albumFound.get().getArtist().getName()
                    )
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public AlbumListResponseDTO getAllAlbum(int page, int size) {
        try {
            Page<AlbumEntity> albumEntityPage = albumRepository.findAll(PageRequest.of(page, size));
            List<AlbumEntity> albumEntityList = albumEntityPage.getContent();

            List<AlbumDataDTO> albumDataDTOList = new ArrayList<>();
            albumEntityList.forEach(albumEntity -> albumDataDTOList.add(
                    new AlbumDataDTO(albumEntity.getTitle(), albumEntity.getReleaseDate(), albumEntity.getArtist().getName()))
            );

            return new AlbumListResponseDTO(
                    true,
                    "Albums found successfully",
                    albumDataDTOList
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public AlbumDeleteResponseDTO deleteAlbumById(Long albumId) {
        try {
            Optional<AlbumEntity> albumFound = albumRepository.findById(albumId);

            if (albumFound.isEmpty()) {
                throw new AlbumNotFoundException("Album not found");
            }

            albumRepository.delete(albumFound.get());

            return new AlbumDeleteResponseDTO(
                    true,
                    "Album delete successfully"
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private AlbumEntity getExistingAlbum(String albumTitle){
        try {
            Optional<AlbumEntity> albumExisting = albumRepository.findByTitle(albumTitle);

            if(albumExisting.isPresent()){
                throw new InvalidAlbumTitleException("Album title already exists");
            }

            return albumExisting.get();
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private void validateDateAlbum(AlbumRequestDTO albumRequestDTO) {
        if(albumRequestDTO.title() == null || albumRequestDTO.title().isEmpty()) {
            throw new InvalidAlbumTitleException("Title cannot be empty");
        }

        if(albumRequestDTO.artistName() == null || albumRequestDTO.artistName().isEmpty()) {
            throw new InvalidArtistNameException("Artist name cannot be empty");
        }

        if(albumRequestDTO.releaseDate().isBefore(ChronoLocalDateTime.from(LocalDateTime.now()))){
            throw new InvalidAlbumReleaseDateException("Release date cannot be in the past");
        }
    }
}
