package org.leonardo.music_streaming_management.repository.music;

import org.leonardo.music_streaming_management.model.music.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long> {
    Optional<MusicEntity> findByTitle(String title);
}
