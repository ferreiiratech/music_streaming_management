package org.leonardo.music_streaming_management.repository.music;

import org.leonardo.music_streaming_management.model.music.MusicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMusicRepository extends JpaRepository<MusicEntity, Long> {
    Optional<MusicEntity> findByTitle(String title);
    Page<MusicEntity> findAll(Pageable pageable);
}
