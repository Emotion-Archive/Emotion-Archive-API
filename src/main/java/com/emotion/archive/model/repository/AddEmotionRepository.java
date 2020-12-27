package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.AddEmotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AddEmotionRepository extends JpaRepository<AddEmotion, Long> {

    @Transactional
    @Query("SELECT a FROM AddEmotion a WHERE a.id = ?1")
    AddEmotion findAddEmotionById(Long id);

    List<AddEmotion> findAllByUserIdAndArchiveTypeOrderByEmotion(Long userId, String archiveType);

}
