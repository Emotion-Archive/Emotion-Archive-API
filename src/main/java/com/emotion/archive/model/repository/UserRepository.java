package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
