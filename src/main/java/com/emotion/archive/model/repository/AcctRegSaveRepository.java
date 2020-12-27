package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.AcctRegSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AcctRegSaveRepository extends JpaRepository<AcctRegSave, Long> {

    @Transactional
    @Query(value = "CALL ACCT_SAVE(?1, ?2, ?3) ", nativeQuery = true)
    void countingRegSave(Long userId, String archiveType, String emotion);

    @Transactional
    @Query("SELECT r FROM AcctRegSave r WHERE r.user.id = ?1 ORDER BY r.regMonth ")
    List<AcctRegSave> getAcctDataByUserId(Long userId);

}
