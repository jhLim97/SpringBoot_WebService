package com.springboot.study.config.auth;

import com.springboot.study.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // csrf().disable().headers().frameOptions().disable() -> h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                    .authorizeRequests()// URL 별 권한 관리를 설정하는 옵션의 시작점 -> 권한 관리 대상을 지정하는 antMatcher 옵션(URL, HTTP 메서드 별로 관리 가능)을 사용할 수 있음
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // "/" 등으로 지정된 URL 들에 permitAll() 옵션을 통해 전체 열람 권한 부여
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // "/api/v1/**" 주소를 가진 API 는 USER 권한을 가진 사람만 가능하도록 설정
                    .anyRequest().authenticated() // anyRequest() 는 설정된 값들 이외 나머지 URL 지칭하며, authenticated() 을 추가해 모두 인증된(로그인한) 사용자들에게만 허용하도록 설정
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // logout()은 로그아웃 기능에 대한 여러 설정의 진입점으로, 로그아웃 성공 시 "/" 주소로 이동
                .and()
                    .oauth2Login() // Oauth 2로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint() // Oauth 2로그인 성공 후 사용자 정보를 가져올 때의 설정들을 담당
                            .userService(customOAuth2UserService); // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                                                                   // + 리서스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
    }
}
