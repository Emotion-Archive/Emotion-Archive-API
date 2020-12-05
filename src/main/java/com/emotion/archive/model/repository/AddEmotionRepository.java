package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.AddEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddEmotionRepository extends JpaRepository<AddEmotion, Long> {
}
