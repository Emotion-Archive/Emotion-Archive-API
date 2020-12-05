package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.AcctRegSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AcctRegSaveRepository extends JpaRepository<AcctRegSave, Long> {

    @Transactional
    @Query(value = "CALL ACCT_SAVE(?1, ?2, ?3) ", nativeQuery = true)
    void countingRegSave(Long userId, String archiveType, String emotion);

}
