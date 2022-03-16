package jpa.springDataJpaStudy.hello.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class HelloMember {

    @Id @GeneratedValue
    private Long id;

    private String username;

    protected HelloMember(){  // Entity는 기본 생성자가 하나 필요하다.
    }

    public HelloMember(String username) {
        this.username = username;
    }
}
