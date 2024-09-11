package org.leonardo.music_streaming_management.repository.album;

import org.leonardo.music_streaming_management.model.album.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
}
