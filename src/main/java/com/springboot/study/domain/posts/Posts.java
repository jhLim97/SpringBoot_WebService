package com.springboot.study.domain.posts;

import com.springboot.study.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter // 롬복의 어노테이션 : 클래스 내 모든 필드의 Getter 메서드 자동 생성
@NoArgsConstructor // 롬복의 어노테이션 : 기본 생성자 자동 추가 public Posts() {} 와 같은 효과
@Entity // JPA 의 어노테이션(주요 어노테이션을 클래스에 가깝게.. 이건 개성에 따라) : 테이블과 링크될 클래스임을 나타내며, 기본값으로 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭
public class Posts extends BaseTimeEntity { // JPA 사용 시 DB 작업할 경우 실제 쿼리를 날리는 것이 아닌, 이 Entity 클래스의 수정을 통해 작업

    @Id // 해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 의 생성 규칙을 나타내며, 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment 가 됨
    private Long id;

    // 테이블의 칼럼을 굳이 선언하지 않아도 해당 클래스의 필드는 모두 칼럼이됨 -> 기본값 외에 추가로 변경이 필요한 옵션이 있는 경우 사용
    @Column(length = 500, nullable = false) // 문자열의 경우 VARCHAR(255) 가 기본값인데 사이즈를 500으로 늘리고 싶은경우
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // 타입을 TEXT 로 변경하고 싶은경우
    private String content;

    private String author;

    @Builder // 롬복의 어노테이션 : 해당 클래스의 빌더 패턴 클래스를 생성(생성자 상단에 선언 시 생성자에 포함된 빌드만 빌더에 포함)
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
