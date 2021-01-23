package com.springboot.study.service.posts;

import com.springboot.study.domain.posts.Posts;
import com.springboot.study.domain.posts.PostsRepository;
import com.springboot.study.web.dto.PostsListResponseDto;
import com.springboot.study.web.dto.PostsResponseDto;
import com.springboot.study.web.dto.PostsSaveRequestDto;
import com.springboot.study.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) { // JPA 의 영속성 컨텍스트 때문에 따로 DB로 쿼리를 날리는 부분이 없음
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. ID="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. ID=" + id)); // 존재하는지 체크 후 아래 코드에서 삭제

        postsRepository.delete(posts); // JpaRepository 에서 기본적으로 제공하는 delete() 메사드 사용(파라미터로 엔티티 대신 id로 삭제할 수 있음 -> deleteById 메서드 사용)
    }

    @Transactional(readOnly = true) // 책에는 없었지만 깃허브 소스코드를 보고 추가
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. ID="+ id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }


}
