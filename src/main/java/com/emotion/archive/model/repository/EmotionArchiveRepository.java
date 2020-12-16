package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.EmotionArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface EmotionArchiveRepository extends JpaRepository<EmotionArchive, Long> {

    @Transactional
    @Query("SELECT e FROM EmotionArchive e WHERE e.id = ?1")
    EmotionArchive findByEmotionArchiveId(Long id);

    EmotionArchive findByIdAndDelYn(Long id, String delYn);

    List<EmotionArchive> findAllByUserIdAndTypeAndDelYnOrderByRegDtDesc(Long userId, String type, String delYn);

    @Transactional
    @Query("SELECT e FROM EmotionArchive e WHERE e.user.id = ?1 AND e.delYn = 'N' AND e.regDt BETWEEN ?2 AND ?3 ")
    List<EmotionArchive> findAllByMonth(Long userId, String startDt, String endDt);

    @Transactional
    @Query("SELECT e FROM EmotionArchive e WHERE e.user.id = ?1 AND e.type = ?2 AND e.delYn = 'N' AND e.regDt <= ?3 ")
    List<EmotionArchive> findAllByDate(Long userId, String type, String date);

}
