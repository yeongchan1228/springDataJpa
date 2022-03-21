package jpa.springDataJpaStudy.datajpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {

//    @Id @GeneratedValue
//    private Long id;

    @Id
    private String id; // Pk가 문자형 객체에 자신이 값을 할당할 때, 스프링 데이터 JPA의 save를 오버라이드 해야 한다.

    @CreatedDate
    private LocalDateTime createdDate;

    public Item(String id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist(){
        createdDate = LocalDateTime.now();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
