package com.springboot.study.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> { // Posts 클래스로 Database 를 접근하게 해줄 JpaRepository -> 기본적인 C, R, U, D 자동 생성

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // 전체 조회 화면 만들기에 필요한 쿼리문문
    List<Posts> findAllDesc();

}
