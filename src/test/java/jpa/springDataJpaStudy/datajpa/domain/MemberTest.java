package jpa.springDataJpaStudy.datajpa.domain;

import jpa.springDataJpaStudy.datajpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired MemberRepository memberRepository;

    @Test
    public void testEntity() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        // when
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();

        List<Member> results = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        // then
        for (Member result : results) {
            System.out.println("result = " + result);
            System.out.println("result.Team = " + result.getTeam());
        }

    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        // given
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);// @PrePersist 실행

        Thread.sleep(100);
        saveMember.setUsername("memberB");

        em.flush(); // @PreUpdate 실행
        em.clear();

        // when
        Member findMember = memberRepository.findById(member.getId()).get();

        // then
        System.out.println(findMember.getCreateDate());
        //System.out.println(findMember.getUpdateDate());
    }

    @Test
    public void EventBaseEntity() throws Exception {
        // given
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);// @PrePersist 실행

        Thread.sleep(100);
        saveMember.setUsername("memberB");

        em.flush(); // @PreUpdate 실행
        em.clear();

        // when
        Member findMember = memberRepository.findById(member.getId()).get();

        // then
        System.out.println(findMember.getCreateDate());
        System.out.println(findMember.getLastModifiedDate());
        System.out.println(findMember.getCreateBy());
        System.out.println(findMember.getLastModifiedBy() );
    }
}