package org.leonardo.music_streaming_management.config;

import org.leonardo.music_streaming_management.model.album.AlbumEntity;
import org.leonardo.music_streaming_management.model.artist.ArtistEntity;
import org.leonardo.music_streaming_management.model.music.MusicEntity;
import org.leonardo.music_streaming_management.repository.album.IAlbumRepository;
import org.leonardo.music_streaming_management.repository.artist.IArtistRepository;
import org.leonardo.music_streaming_management.repository.music.IMusicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(IMusicRepository musicRepository, IAlbumRepository albumRepository, IArtistRepository artistRepository) {
        return args -> {
            musicRepository.deleteAll();
            albumRepository.deleteAll();
            artistRepository.deleteAll();

            // Artists
            ArtistEntity artist1 = new ArtistEntity();
            artist1.setName("Caetano Veloso");
            artist1.setNationality("Brasileiro");

            ArtistEntity artist2 = new ArtistEntity();
            artist2.setName("Elis Regina");
            artist2.setNationality("Brasileira");

            ArtistEntity artist3 = new ArtistEntity();
            artist3.setName("Gilberto Gil");
            artist3.setNationality("Brasileiro");

            artistRepository.saveAll(List.of(artist1, artist2, artist3));


            // Albums
            AlbumEntity album1 = new AlbumEntity();
            album1.setTitle("Tropicália ou Panis et Circensis");
            album1.setReleaseDate(LocalDateTime.of(1968, 7, 1, 0, 0));
            album1.setArtist(artist1);

            AlbumEntity album2 = new AlbumEntity();
            album2.setTitle("Elis & Tom");
            album2.setReleaseDate(LocalDateTime.of(1974, 4, 30, 0, 0));
            album2.setArtist(artist2);

            AlbumEntity album3 = new AlbumEntity();
            album3.setTitle("Refazenda");
            album3.setReleaseDate(LocalDateTime.of(1975, 8, 10, 0, 0));
            album3.setArtist(artist3);

            albumRepository.saveAll(List.of(album1, album2, album3));


            // Musics
            MusicEntity music1 = new MusicEntity();
            music1.setTitle("Alegria, Alegria");
            music1.setGenre("MPB");
            music1.setDuration(240);
            music1.setReleaseDate(LocalDateTime.of(1967, 10, 15, 0, 0));
            music1.setArtist(artist1);
            music1.setAlbum(album1);

            MusicEntity music2 = new MusicEntity();
            music2.setTitle("Águas de Março");
            music2.setGenre("MPB");
            music2.setDuration(195);
            music2.setReleaseDate(LocalDateTime.of(1974, 4, 30, 0, 0));
            music2.setArtist(artist2);
            music2.setAlbum(album2);

            MusicEntity music3 = new MusicEntity();
            music3.setTitle("Refazenda");
            music3.setGenre("MPB");
            music3.setDuration(225);
            music3.setReleaseDate(LocalDateTime.of(1975, 8, 10, 0, 0));
            music3.setArtist(artist3);
            music3.setAlbum(album3);

            musicRepository.saveAll(List.of(music1, music2, music3));

            System.out.println("Dados iniciais carregados com sucesso!");
        };
    }
}
