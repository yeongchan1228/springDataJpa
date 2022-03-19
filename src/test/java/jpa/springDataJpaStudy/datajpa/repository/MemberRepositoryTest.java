package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Member;
import jpa.springDataJpaStudy.datajpa.domain.Team;
import jpa.springDataJpaStudy.datajpa.repository.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired EntityManager em;

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
    
    @Test
    public void findByNames() throws Exception {
        // given
        Member member1 = new Member("member1");
        member1.setAge(10);

        Member member2 = new Member("member2");
        member2.setAge(20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> results = memberRepository.findByNames(Arrays.asList(member1.getUsername(), member2.getUsername()));

        // then
        assertThat(results.size()).isEqualTo(2);
        
    }
    
    @Test
    public void returnType() throws Exception {
        // given
        Member member1 = new Member("member1");
        member1.setAge(10);

        Member member2 = new Member("member2");
        member2.setAge(20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> findMember1 = memberRepository.findListByUsername("member1");
        Member findMember2 = memberRepository.findMemberByUsername("member2");// 이름이 유니크 하다면 단건 조회
        Optional<Member> findMember3 = memberRepository.findOptionalByUsername("aasdasd");

        // then
        System.out.println("findMember1 = " + findMember1); // 실무에선 Assertions로 검사
        System.out.println("findMember2 = " + findMember2); // 실무에선 Assertions로 검사
        System.out.println("findMember3 = " + findMember3.orElse(null)); // 실무에선 Assertions로 검사
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

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);
        memberRepository.save(member7);
        memberRepository.save(member8);

        // 0번 부터 3개, 유저 이름을 기반으로 오름차순으로 // 3으로 나누겠다. 3으로 페이징
        PageRequest request = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> page = memberRepository.findByAge(10, request);
        //API에서는 Dto로 결과를 반환해서 뿌려야 하므로
        //page.map(member -> new MemberDto(member.getId() ...))

        // then
        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(8);
        assertThat(page.getNumber()).isEqualTo(0); // 현재 페이지
        assertThat(page.getTotalPages()).isEqualTo(3); // 3개로 나눴을 때 총 페이지
        assertThat(page.isFirst()).isTrue(); // 해당 페이지 첫번 째 페이지인가.
        assertThat(page.hasNext()).isTrue(); // 다음 페이지가 있는가

        //
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        PageRequest pageRequest2 = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "username"));

        //slice는 3개가 아니라 +1인 4개를 가져온다. limit가 4개
        Slice<Member> slice = memberRepository.findSliceByAge(10, pageRequest);
        Slice<Member> slice2 = memberRepository.findSliceByAge(10, pageRequest2);

        assertThat(slice.getContent().size()).isEqualTo(3);
        assertThat(slice.hasNext()).isTrue();

        assertThat(slice2.hasNext()).isFalse();

        List<Member> list = memberRepository.findListByAge(10, pageRequest);
        assertThat(list.size()).isEqualTo(3);

    }

    @Test
    public void bulkAgePlus() throws Exception {
        // given
        Member member1 = new Member("member1");
        member1.setAge(10);
        Member member2 = new Member("member2");
        member2.setAge(20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        // 벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 때문에 결과가 달라질 수 있어 항상 전에 영속성 컨텍스트를 비워야한다.
//        em.flush();
//        em.clear();

        int result = memberRepository.bulkAgePlus(10);
        Optional<Member> findMember = memberRepository.findById(member1.getId());

        // then
        assertThat(findMember.get().getAge()).isEqualTo(11);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void joinFetchTest() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("memberA", 10, teamA);
        Member member2 = new Member("memberB", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();
        
        // when
        // 쿼리를 멤버 찾는 쿼리 하나로 해결 가능
        //List<Member> findMembers = memberRepository.findMemberFetchJoin();
        //List<Member> findMembers = memberRepository.findAll();
        //List<Member> findMembers = memberRepository.findMemberEntityGraph();
        //List<Member> findMembers = memberRepository.findEntityGraphByUsername(member1.getUsername());
        List<Member> findMembers = memberRepository.findEntityGraph2ByUsername(member1.getUsername());

        // then
        for (Member findMember : findMembers) {
            System.out.println("findMember.getTeam().getClass() = " + findMember.getTeam().getClass());
            System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());
        }

    }
}