package com.emotion.archive.model.repository;

import com.emotion.archive.model.dto.StatisticsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StatisticsDTORepository extends JpaRepository<StatisticsDTO, String> {

    @Transactional
    @Query(value = "SELECT LEFT(e.REG_DT, 6) AS REG_MONTH, e.TYPE as ARCHIVE_TYPE, COUNT(*) AS COUNT FROM EMOTION_ARCHIVE e WHERE e.USER_ID = ?1 AND e.REG_DT >= ?2 GROUP BY e.TYPE ", nativeQuery = true)
    List<StatisticsDTO> getStatisticsData(Long userId, String regDt);

}
