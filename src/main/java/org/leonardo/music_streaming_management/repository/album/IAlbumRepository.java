package org.leonardo.music_streaming_management.repository.album;

import org.leonardo.music_streaming_management.model.album.AlbumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Optional<AlbumEntity> findByTitle(String album);
    Page<AlbumEntity> findAll(Pageable pageable);
}
