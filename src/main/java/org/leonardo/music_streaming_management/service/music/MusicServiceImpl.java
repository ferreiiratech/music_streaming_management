package org.leonardo.music_streaming_management.service.music;

import org.leonardo.music_streaming_management.config.exception.AccessDatabaseFailureException;
import org.leonardo.music_streaming_management.dto.music.*;
import org.leonardo.music_streaming_management.model.album.AlbumEntity;
import org.leonardo.music_streaming_management.model.artist.ArtistEntity;
import org.leonardo.music_streaming_management.model.music.MusicEntity;
import org.leonardo.music_streaming_management.model.music.exception.*;
import org.leonardo.music_streaming_management.repository.album.AlbumRepository;
import org.leonardo.music_streaming_management.repository.artist.ArtistRepository;
import org.leonardo.music_streaming_management.repository.music.MusicRepository;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;

@Service
public class MusicServiceImpl implements IMusicService {
    private final MusicRepository musicRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public MusicServiceImpl(
            MusicRepository musicRepository,
            AlbumRepository albumRepository,
            ArtistRepository artistRepository
    ) {
        this.musicRepository = musicRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public MusicCreatedResponseDTO createMusic(MusicRequestDTO musicRequestDTO) {
        try{
            validateMusicDate(musicRequestDTO);

            Optional<MusicEntity> musicFound = musicRepository.findByTitle(musicRequestDTO.title());

            if(musicFound.isPresent()) {
                throw new InvalidMusicTitleException("Music title already exists");
            }

            ArtistEntity artistFound = getExistingArtist(musicRequestDTO.artist());

            AlbumEntity albumFound = getExistingAlbum(musicRequestDTO.album());

            MusicEntity musicEntity = new MusicEntity();
            BeanUtils.copyProperties(musicRequestDTO, musicEntity);
            musicEntity.setAlbum(albumFound);
            musicEntity.setArtist(artistFound);

            MusicEntity musicCreated = musicRepository.save(musicEntity);

            return new MusicCreatedResponseDTO(
                    true,
                    "Music created with success",
                    new MusicDataDTO(
                            musicCreated.getTitle(),
                            musicCreated.getGenre(),
                            musicCreated.getDuration(),
                            musicCreated.getReleaseDate(),
                            musicCreated.getArtist().getName(),
                            musicCreated.getAlbum().getTitle()
                    )
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public MusicUpdatedResponseDTO updateMusic(Long musicId, MusicRequestDTO musicRequestDTO) {
        try {
            validateMusicDate(musicRequestDTO);

            Optional<MusicEntity> musicFound = musicRepository.findById(musicId);

            if(musicFound.isEmpty()) {
                throw new MusicNotFoundException("Music not found");
            }

            ArtistEntity artistFound = getExistingArtist(musicRequestDTO.artist());

            AlbumEntity albumFound = getExistingAlbum(musicRequestDTO.album());

            BeanUtils.copyProperties(musicRequestDTO, musicFound.get());
            musicFound.get().setArtist(artistFound);
            musicFound.get().setAlbum(albumFound);

            MusicEntity musicUpdated = musicRepository.save(musicFound.get());

            return new MusicUpdatedResponseDTO(
                    true,
                    "Music updated with success",
                    new MusicDataDTO(
                            musicUpdated.getTitle(),
                            musicUpdated.getGenre(),
                            musicUpdated.getDuration(),
                            musicUpdated.getReleaseDate(),
                            musicUpdated.getArtist().getName(),
                            musicUpdated.getAlbum().getTitle()
                    )
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public MusicGetResponseDTO getMusicById(Long musicId) {
        try {
            Optional<MusicEntity> musicFound = musicRepository.findById(musicId);

            if(musicFound.isEmpty()) {
                throw new MusicNotFoundException("Music not found");
            }

            return new MusicGetResponseDTO(
                    true,
                    "Music found success",
                    new MusicDataDTO(
                            musicFound.get().getTitle(),
                            musicFound.get().getGenre(),
                            musicFound.get().getDuration(),
                            musicFound.get().getReleaseDate(),
                            musicFound.get().getArtist().getName(),
                            musicFound.get().getAlbum().getTitle()
                    )
            );
        } catch (DataAccessResourceFailureException exception) {
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private AlbumEntity getExistingAlbum(String albumName) {
        try {
            Optional<AlbumEntity> albumFound = albumRepository.findByTitle(albumName);

            if(albumFound.isEmpty()){
                throw new InvalidMusicAlbumException("Music album does not exist");
            }

            return albumFound.get();
        } catch (DataAccessResourceFailureException exception) {
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private ArtistEntity getExistingArtist(String artistName) {
        try {
            Optional<ArtistEntity> artistFound = artistRepository.findByName(artistName);

            if(artistFound.isEmpty()){
                throw new InvalidMusicArtistException("Music artist does not exist");
            }

            return artistFound.get();
        } catch (DataAccessResourceFailureException exception) {
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private void validateMusicDate(MusicRequestDTO musicRequestDTO) {
        if(musicRequestDTO.title() == null || musicRequestDTO.title().isEmpty()) {
            throw new InvalidMusicTitleException("Music title cannot be empty");
        }

        if(musicRequestDTO.genre() == null || musicRequestDTO.genre().isEmpty()) {
            throw new InvalidMusicGenreException("Music genre cannot be empty");
        }

        if(musicRequestDTO.artist() == null || musicRequestDTO.artist().isEmpty()) {
            throw new InvalidMusicArtistException("Music artist cannot be empty");
        }

        if(musicRequestDTO.album() == null || musicRequestDTO.album().isEmpty()) {
            throw new InvalidMusicAlbumException("Music album cannot be empty");
        }

        if(musicRequestDTO.duration() <= 0){
            throw new InvalidMusicDurationException("Music duration cannot be negative or zero");
        }

        if(musicRequestDTO.releaseDate().isBefore(ChronoLocalDateTime.from(LocalDateTime.now()))){
            throw new InvalidMusicReleaseDateException("Music release date cannot be before current date");
        }
    }
}
