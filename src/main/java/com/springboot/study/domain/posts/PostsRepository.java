package com.springboot.study.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> { // Posts 클래스로 Database 를 접근하게 해줄 JpaRepository -> 기본적인 C, R, U, D 자동 생성
}
