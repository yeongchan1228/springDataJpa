package jpa.springDataJpaStudy.hello.repository;

import jpa.springDataJpaStudy.hello.domain.HelloMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class HelloMemberJpaRepository {

    private final EntityManager em;

    public HelloMember save(HelloMember member){
        em.persist(member);

        return member;
    }

    public HelloMember find(Long id){
        return em.find(HelloMember.class, id);
    }
}
