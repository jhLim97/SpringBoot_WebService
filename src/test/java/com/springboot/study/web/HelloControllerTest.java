package com.springboot.study.web;

import com.springboot.study.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test; // Junit4에서 5로 업데이트 되며 변경된 사항(api가 추가됨)
import org.junit.jupiter.api.extension.ExtendWith; // Junit5가 되면서 @RunWith에서 @ExtendWith로 변경
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension; // @RunWith와 마찬가지로 SpringRunner 역시 SpringExtension 로 변경
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) // 테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자 실행, 여기서는 SpringExtension 스프링 실행자를 사용함 -> 즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }) // 여러 스프링 테스트 어노테이션 중 Web(Spring MVC)에 집중할 수 있는 어노테이션으로, 선언 시 @Controller, @ControllerAdvice 등 사용가능 -> 여기서는 컨트롤러만 사용하므로 선언
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 Bean을 주입받음
    private MockMvc mvc; // 웹 API를 테스트 할 때 사용하며, 스프링 MVC 테스트의 시작점 -> HTTP GET, POST 등에 대한 API 테스트를 가능하게 함

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 HTTP GET 요청을 함 + 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언 가능
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증, HTTP Header의 Status를 검증, (200, 404, 500 등의 상태를 검증) -> 여기서는 OK즉, 200 여부 검증
                .andExpect(content().string(hello)); // mvc.perform의 결과를 검증, 응답 본문의 내용을 검증, Controller에서 "hello"를 리턴하므로 이 값이 맞는지 검증

    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name) // API 테스트 시 사용될 요청 파라미터 설정(단, 값은 String 만 가능 -> 숫자, 날짜 등의 데이터로 등록시에는 문자열로..)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // JSON 응답값을 필드별로 검증할 수 있는 메서드 -> $를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
