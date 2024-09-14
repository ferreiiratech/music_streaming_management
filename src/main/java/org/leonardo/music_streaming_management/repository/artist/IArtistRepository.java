package org.leonardo.music_streaming_management.repository.artist;

import org.leonardo.music_streaming_management.model.artist.ArtistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IArtistRepository extends JpaRepository<ArtistEntity, Long> {
    Optional<ArtistEntity> findByName(String artist);
    Page<ArtistEntity> findAll(Pageable pageable);
}
