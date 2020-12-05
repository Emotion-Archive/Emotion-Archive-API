package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.EmotionArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionArchiveRepository extends JpaRepository<EmotionArchive, Long> {
}
