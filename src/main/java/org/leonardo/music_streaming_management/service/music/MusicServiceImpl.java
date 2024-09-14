package org.leonardo.music_streaming_management.service.music;

import org.leonardo.music_streaming_management.config.exception.AccessDatabaseFailureException;
import org.leonardo.music_streaming_management.dto.music.*;
import org.leonardo.music_streaming_management.model.album.AlbumEntity;
import org.leonardo.music_streaming_management.model.artist.ArtistEntity;
import org.leonardo.music_streaming_management.model.music.MusicEntity;
import org.leonardo.music_streaming_management.model.music.exception.*;
import org.leonardo.music_streaming_management.repository.album.AlbumRepository;
import org.leonardo.music_streaming_management.repository.artist.ArtistRepository;
import org.leonardo.music_streaming_management.repository.music.IMusicRepository;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MusicServiceImpl implements IMusicService {
    private final IMusicRepository IMusicRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public MusicServiceImpl(
            IMusicRepository IMusicRepository,
            AlbumRepository albumRepository,
            ArtistRepository artistRepository
    ) {
        this.IMusicRepository = IMusicRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public MusicCreatedResponseDTO createMusic(MusicRequestDTO musicRequestDTO) {
        try{
            validateMusicDate(musicRequestDTO);

            Optional<MusicEntity> musicFound = IMusicRepository.findByTitle(musicRequestDTO.title());

            if(musicFound.isPresent()) {
                throw new InvalidMusicTitleException("Music title already exists");
            }

            ArtistEntity artistFound = getExistingArtist(musicRequestDTO.artist());

            AlbumEntity albumFound = getExistingAlbum(musicRequestDTO.album());

            MusicEntity musicEntity = new MusicEntity();
            BeanUtils.copyProperties(musicRequestDTO, musicEntity);
            musicEntity.setAlbum(albumFound);
            musicEntity.setArtist(artistFound);

            MusicEntity musicCreated = IMusicRepository.save(musicEntity);

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

            MusicEntity musicFound = getExistingMusic(musicId);
            ArtistEntity artistFound = getExistingArtist(musicRequestDTO.artist());
            AlbumEntity albumFound = getExistingAlbum(musicRequestDTO.album());

            BeanUtils.copyProperties(musicRequestDTO, musicFound);
            musicFound.setArtist(artistFound);
            musicFound.setAlbum(albumFound);

            MusicEntity musicUpdated = IMusicRepository.save(musicFound);

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
            MusicEntity musicFound = getExistingMusic(musicId);

            return new MusicGetResponseDTO(
                    true,
                    "Music found success",
                    new MusicDataDTO(
                            musicFound.getTitle(),
                            musicFound.getGenre(),
                            musicFound.getDuration(),
                            musicFound.getReleaseDate(),
                            musicFound.getArtist().getName(),
                            musicFound.getAlbum().getTitle()
                    )
            );
        } catch (DataAccessResourceFailureException exception) {
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public MusicDeleteResponseDTO deleteMusic(Long musicId) {
        try {
            MusicEntity musicFound = getExistingMusic(musicId);

            IMusicRepository.delete(musicFound);

            return new MusicDeleteResponseDTO(
                    true,
                    "Music deleted successfully"
            );
        } catch (DataAccessResourceFailureException exception) {
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    @Override
    public SearchMusicResponseDTO searchMusics(
            Optional<String> musicTitle,
            Optional<String> musicGenre,
            Optional<String> artistName,
            Optional<String> albumTitle,
            int page,
            int size
    ) {
        try {
            Page<MusicEntity> musicEntityPage = IMusicRepository.findAll(PageRequest.of(page, size));
            List<MusicEntity> musicEntityList = musicEntityPage.getContent();

            if(musicTitle.isPresent()) {
                musicEntityList = musicEntityList.stream()
                        .filter(musicEntity -> musicEntity
                                .getTitle()
                                .toLowerCase()
                                .contains(musicTitle.get().toLowerCase())
                        ).toList();
            }

            if(musicGenre.isPresent()) {
                musicEntityList = musicEntityList.stream()
                        .filter(musicEntity -> musicEntity
                                .getGenre()
                                .toLowerCase()
                                .contains(musicGenre.get().toLowerCase())
                        ).toList();
            }

            if(artistName.isPresent()) {
                musicEntityList = musicEntityList.stream()
                        .filter(musicEntity -> musicEntity
                                .getArtist().getName()
                                .toLowerCase()
                                .contains(artistName.get().toLowerCase())
                        ).toList();
            }

            if(albumTitle.isPresent()) {
                musicEntityList = musicEntityList.stream()
                        .filter(musicEntity -> musicEntity
                                .getAlbum().getTitle()
                                .toLowerCase()
                                .contains(albumTitle.get().toLowerCase())
                        ).toList();
            }

            List<MusicDataDTO> musicDataDTOList = new ArrayList<>();
            musicEntityList.forEach(musicEntity -> musicDataDTOList.add(
                    new MusicDataDTO(
                            musicEntity.getTitle(),
                            musicEntity.getGenre(),
                            musicEntity.getDuration(),
                            musicEntity.getReleaseDate(),
                            musicEntity.getArtist().getName(),
                            musicEntity.getAlbum().getTitle()
                    )
            ));

            return new SearchMusicResponseDTO(
                    true,
                    "Pesquisa realizada com sucesso",
                    musicDataDTOList
            );
        } catch (DataAccessResourceFailureException exception){
            throw new AccessDatabaseFailureException("An internal error has occurred. Try again later");
        }
    }

    private MusicEntity getExistingMusic(Long musicId) {
        try {
            Optional<MusicEntity> musicFound = IMusicRepository.findById(musicId);

            if(musicFound.isEmpty()) {
                throw new MusicNotFoundException("Music not found");
            }

            return musicFound.get();
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
