package jpa.springDataJpaStudy.datajpa.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass // 데이터 필드 상속 관계 정의
@Getter
public class JpaBaseEntity {

    @Column(updatable = false) // db 값 변경 제한
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @PrePersist // 저장하기 전에
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.updateDate = now;
    }

    @PreUpdate // 업데이트 전에
    public void preUpdate(){
        updateDate = LocalDateTime.now();
    }

}
