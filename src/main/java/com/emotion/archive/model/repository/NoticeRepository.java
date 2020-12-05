package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
