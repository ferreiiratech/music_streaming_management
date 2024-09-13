package org.leonardo.music_streaming_management.service.music;

import org.leonardo.music_streaming_management.config.exception.AccessDatabaseFailureException;
import org.leonardo.music_streaming_management.dto.music.MusicCreateRequestDTO;
import org.leonardo.music_streaming_management.dto.music.MusicCreatedDataDTO;
import org.leonardo.music_streaming_management.dto.music.MusicCreatedResponseDTO;
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

    public MusicServiceImpl(MusicRepository musicRepository, AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.musicRepository = musicRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public MusicCreatedResponseDTO createMusic(MusicCreateRequestDTO musicCreateRequestDTO) {
        try{
            validateMusicDate(musicCreateRequestDTO);

            Optional<MusicEntity> musicFound = musicRepository.findByTitle(musicCreateRequestDTO.title());

            if(musicFound.isPresent()) {
                throw new InvalidMusicTitleException("Music title already exists");
            }

            Optional<ArtistEntity> artistFound = artistRepository.findByName(musicCreateRequestDTO.artist());

            if(artistFound.isEmpty()){
                throw new InvalidMusicArtistException("Music artist does not exist");
            }

            Optional<AlbumEntity> albumFound = albumRepository.findByTitle(musicCreateRequestDTO.album());

            if(albumFound.isEmpty()){
                throw new InvalidMusicAlbumException("Music album does not exist");
            }

            MusicEntity musicCreated = new MusicEntity();
            BeanUtils.copyProperties(musicCreateRequestDTO, musicCreated);
            musicCreated.setAlbum(albumFound.get());
            musicCreated.setArtist(artistFound.get());

            return new MusicCreatedResponseDTO(
                    true,
                    "Music created with success",
                    new MusicCreatedDataDTO(
                            musicCreated.getTitle(),
                            musicCreated.getGenre(),
                            musicCreated.getDuration(),
                            musicCreated.getReleaseDate().toLocalDate(),
                            musicCreated.getArtist().getName(),
                            musicCreated.getAlbum().getTitle()
                    )
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private void validateMusicDate(MusicCreateRequestDTO musicCreateRequestDTO) {
        if(musicCreateRequestDTO.title() == null || musicCreateRequestDTO.title().isEmpty()) {
            throw new InvalidMusicTitleException("Music title cannot be empty");
        }

        if(musicCreateRequestDTO.genre() == null || musicCreateRequestDTO.genre().isEmpty()) {
            throw new InvalidMusicGenreException("Music genre cannot be empty");
        }

        if(musicCreateRequestDTO.artist() == null || musicCreateRequestDTO.artist().isEmpty()) {
            throw new InvalidMusicArtistException("Music artist cannot be empty");
        }

        if(musicCreateRequestDTO.album() == null || musicCreateRequestDTO.album().isEmpty()) {
            throw new InvalidMusicAlbumException("Music album cannot be empty");
        }

        if(musicCreateRequestDTO.duration() <= 0){
            throw new InvalidMusicDurationException("Music duration cannot be negative or zero");
        }

        if(musicCreateRequestDTO.releaseDate().isBefore(ChronoLocalDateTime.from(LocalDateTime.now()))){
            throw new InvalidMusicReleaseDateException("Music release date cannot be before current date");
        }
    }
}
