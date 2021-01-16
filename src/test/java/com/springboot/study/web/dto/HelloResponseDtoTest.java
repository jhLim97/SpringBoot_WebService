package com.springboot.study.web.dto;

import org.junit.jupiter.api.Test; // Junit4에서 5로 업데이트 되며 변경된 사항(api가 추가됨)
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() { // getter 메서드와 생성자가 정상적으로 생성되는지 test
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);     // assertThat 은 assertj 라는 검증 라이브러리의 검증 메소드 -> 검증하고 싶은 대상을 메소드 인자로 받음
        assertThat(dto.getAmount()).isEqualTo(amount); // Junit 의 기본 assertThat 이 아닌 assertj 의 assertThat 를 사용한 이유는 추가적인 라이브러리가 필요하지 않으며 보다 더 확실한 자동완성 기능 제공
    }
}
