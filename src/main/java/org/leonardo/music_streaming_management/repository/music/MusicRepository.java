package org.leonardo.music_streaming_management.repository.music;

import org.leonardo.music_streaming_management.model.music.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long> {
}
