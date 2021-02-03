package com.springboot.study.web;

import com.springboot.study.config.auth.LoginUser;
import com.springboot.study.config.auth.dto.SessionUser;
import com.springboot.study.service.posts.PostsService;
import com.springboot.study.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { // Model 은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장하는 데 사용
        model.addAttribute("posts", postsService.findAllDesc()); // postsService.findAllDesc()로 가져온 결과를 posts 로 index.mustache 에 전달
        //SessionUser user = (SessionUser) httpSession.getAttribute("user"); // CustomOAuthUserService 에서 로그인 성공 시 세션에 SessionUser 를 저장하도록 했으므로 httpSession.getAttribute("user") 로 값을 가져올 수 있음
        if(user != null) {
            model.addAttribute("userName", user.getName()); // index.mustache 에서 userName 을 사용할 수 있도록(단, 세션에 저장된 값이 있을때만 model 에 userName 으로 저장됨)
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
