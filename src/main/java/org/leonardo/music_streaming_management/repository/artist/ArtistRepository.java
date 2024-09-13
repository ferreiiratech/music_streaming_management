package org.leonardo.music_streaming_management.repository.artist;

import org.leonardo.music_streaming_management.model.artist.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    Optional<ArtistEntity> findByName(String artist);
}
