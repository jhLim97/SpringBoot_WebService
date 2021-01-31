package com.springboot.study.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // 소셜 로그인으로 반환되는 값 중 email 을 통해 이미 생성된 사용자인지 처음인지 판단하는 메서드
}
