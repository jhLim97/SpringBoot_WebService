package com.springboot.study.web; // 테스트 코드 실습에서 controller들을 담는 패키지

import com.springboot.study.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam; // @RequestParam 는 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 반환하는 컨트롤러로...
public class HelloController {

    @GetMapping("/hello") // HTTP Method인 Get의 요청을 받을 수 있는 API 제작
    public String hello() { // /hello 요청들어 올시 문자열 hello 반환하는 기능 구현
        return "hello";
    }

    @GetMapping("/hello/dto") // 여기서는 외부에서 name(@RequestParam("name")) 이란 이름으로 넘긴 파라미터를 메서드 파라미터 name(String name) 에 저장.. amount도 마찬가지
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
