package jpa.springDataJpaStudy.hello.repository;

import jpa.springDataJpaStudy.hello.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;

    public Member save(Member member){
        em.persist(member);

        return member;
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
