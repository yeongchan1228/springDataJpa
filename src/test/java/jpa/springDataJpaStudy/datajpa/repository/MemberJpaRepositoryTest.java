package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {
    
    @Autowired MemberJpaRepository memberJpaRepository;
    @Autowired EntityManager em;
    
    @Test
    public void basicCRUD() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        
        // when
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        // then
        // 단건 조회 검증
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
        
    }
    
    @Test
    public void findByUsernameAndAgeGraterThen() throws Exception {
        // given
        Member member1 = new Member("member1");
        member1.setAge(10);

        Member member2 = new Member("member1");
        member2.setAge(20);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // when
        List<Member> results = memberJpaRepository.findByUsernameAndAgeGraterThan(member1.getUsername(), 14);

        // then
        assertThat(results.get(0).getUsername()).isEqualTo("member1");
        assertThat(results.get(0).getAge()).isEqualTo(20);
    }
    
    @Test
    public void paging() throws Exception {
        // given
        Member member1 = new Member("member1");
        member1.setAge(10);
        Member member2 = new Member("member2");
        member2.setAge(10);
        Member member3 = new Member("member3");
        member3.setAge(10);
        Member member4 = new Member("member4");
        member4.setAge(10);
        Member member5 = new Member("member5");
        member5.setAge(10);
        Member member6 = new Member("member6");
        member6.setAge(10);
        Member member7 = new Member("member7");
        member7.setAge(10);
        Member member8 = new Member("member8");
        member8.setAge(10);
        
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);
        memberJpaRepository.save(member5);
        memberJpaRepository.save(member6);
        memberJpaRepository.save(member7);
        memberJpaRepository.save(member8);
        
        // when
        List<Member> findMembers = memberJpaRepository.findByPage(10, 0, 4);
        long count = memberJpaRepository.totalPage(10);

        // then
        assertThat(findMembers.size()).isEqualTo(4);
        assertThat(count).isEqualTo(8);
        
    }

    @Test
    public void bulkUpdate() throws Exception {
        // given
        Member member1 = new Member("member1");
        member1.setAge(10);
        Member member2 = new Member("member2");
        member2.setAge(20);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // when
        // 벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 때문에 결과가 달라질 수 있어 항상 전에 영속성 컨텍스트를 비워야한다.
        em.flush();
        em.clear();

        int result = memberJpaRepository.bulkAgePlus(10);
        Optional<Member> findMember = memberJpaRepository.findById(member1.getId());

        // then
        assertThat(findMember.get().getAge()).isEqualTo(11);
        assertThat(result).isEqualTo(2);
    }
}