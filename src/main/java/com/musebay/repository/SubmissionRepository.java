package com.musebay.repository;

import com.musebay.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    // ✅ 이메일 중복 확인
    boolean existsByEmail(String email);

    // ✅ 이메일로 Submission 조회 (디테일 뷰 & 승인용)
    Optional<Submission> findByEmail(String email);
}
