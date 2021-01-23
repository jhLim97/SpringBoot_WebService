package com.springboot.study.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity 를 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 설정
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity 클래스에 Auditing 기능 포함되도록..
public class BaseTimeEntity { // 모든 Entity 의 상위 클래스가 되어 Entity 들의 createdDate, modifiedDate 를 자동으로 관리하는 역할

    @CreatedDate // Entity 가 생성되어 저장 될때 시간이 자동 저장됨
    private LocalDateTime createdDate;

    @LastModifiedDate // 조회한 Entity 의 값을 변경할 때 시간이 자동 저장됨
    private LocalDateTime modifiedDate;
}
