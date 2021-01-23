package com.springboot.study.web;

import com.springboot.study.service.posts.PostsService;
import com.springboot.study.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) { // Model 은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장하는 데 사용
        model.addAttribute("posts", postsService.findAllDesc()); // postsService.findAllDesc()로 가져온 결과를 posts 로 index.mustache 에 전달
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
