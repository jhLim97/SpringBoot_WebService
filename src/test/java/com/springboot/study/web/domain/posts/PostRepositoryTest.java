package com.springboot.study.web.domain.posts;

import com.springboot.study.domain.posts.Posts;
import com.springboot.study.domain.posts.PostsRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest // 해당 어노테이션 사용 시 별다른 설정 없이 H2 데이터베이스 자동 실행
public class PostRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach // Junit 에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정 -> 보통 배포 전 전체 테스트를 수행할 때 테스트 간 데이터 침범을 막기위해 사용(여러 테스트 동시 수행 시 테스트용 데베인 H2에 데이터가 남아 다음 테스트 실행에 영향주므로)
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder() // 테이블 posts 에 insert/update 쿼리 실행 -> id 즉, PK가 있으면 update 없다면 insert 쿼리 실행
                .title(title)
                .content(content)
                .author("dlawnsgur1118@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll(); // 테이블 posts에 있는 모든 데이터를 조회해오는 메서드

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}
