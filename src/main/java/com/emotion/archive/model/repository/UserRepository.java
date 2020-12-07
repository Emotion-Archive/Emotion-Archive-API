package com.emotion.archive.model.repository;

import com.emotion.archive.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhone(String phone);

    @Transactional
    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findByUserId(Long id);

    @Transactional
    @Query(value = "CALL UPDATE_USER_ROLE(?1)", nativeQuery = true)
    String updateUserRole(Long id);

    List<User> getAllByOrderByRegDtDesc();

    @Transactional
    boolean existsById(Long id);

}
