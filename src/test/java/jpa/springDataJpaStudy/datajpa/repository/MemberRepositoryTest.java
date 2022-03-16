package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Member;
import jpa.springDataJpaStudy.datajpa.domain.Team;
import jpa.springDataJpaStudy.datajpa.repository.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    public void basicCRUD() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        // then
        // 단건 조회 검증
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);

    }

    @Test
    public void findByUsernameAndAgeGraterThan() throws Exception {
        // given
        Member member1 = new Member("member1");
        member1.setAge(10);

        Member member2 = new Member("member1");
        member2.setAge(20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> results = memberRepository.findByUsernameAndAgeGreaterThan(member1.getUsername(), 14);

        // then
        assertThat(results.get(0).getUsername()).isEqualTo("member1");
        assertThat(results.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void namedQueryTest() throws Exception {
        // given
        Member member1 = new Member("member1");

        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> findMember = memberRepository.findByUsername(member1.getUsername());

        // then
        assertThat(findMember.get(0).getUsername()).isEqualTo(member1.getUsername());

    }
    
    @Test
    public void testRepositoryQuery() throws Exception {
        // given
        Member member1 = new Member("member1");
        member1.setAge(10);

        Member member2 = new Member("member2");
        member2.setAge(20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> findUser = memberRepository.findUser(member1.getUsername(), member1.getAge());

        // then
        assertThat(findUser.get(0).getUsername()).isEqualTo(member1.getUsername());
        
    }

    @Test
    public void testQueryDto() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1");
        member1.setAge(10);
        member1.changeTeam(teamA);

        Member member2 = new Member("member2");
        member2.setAge(20);
        member2.changeTeam(teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<MemberDto> memberDtos = memberRepository.findMemberDto();

        // then
        assertThat(memberDtos.get(0).getUsername()).isEqualTo(member1.getUsername());
        assertThat(memberDtos.get(1).getUsername()).isEqualTo(member2.getUsername());
        assertThat(memberDtos.get(0).getTeamname()).isEqualTo(member1.getTeam().getName());
        assertThat(memberDtos.get(1).getTeamname()).isEqualTo(member2.getTeam().getName());

    }
}