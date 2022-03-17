package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Member;
import jpa.springDataJpaStudy.datajpa.repository.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQuery;
import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    List<Member> findByUsername(String username); // 쿼리 메서드 기능 -> 이름 조회
//    List<Member> findXXBy(); // 쿼리 메서드 기능 -> 전체 조회
//    List<Member> findTop3By(); // 쿼리 메서드 기능 -> Limit 조회 -> limit3

    /**
     * 쿼리 메서드 기능
     * 메서드 이름을 가지고 쿼리 생성
     */
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /**
     * namedQuery 기능
     * Param 어노테이션에 의해 namedQuery가 도메인에 있는지 확인하고 없으면 그냥 파라미터를 무시하고 메서드 이름을 기준으로 쿼리 실행
     * 보통 안 쓰고 레포지토리에 쿼리를 칠 수 있는 기능을 사용한다.
     */
//    @Query(name = "Member.findByUsername") // 멤버에서 정의한 네임드 쿼리와 동일한 이름 -> 주석 처리 가능, 타입.findByUsername을 먼저 찾기 때문
    List<Member> findByUsername(@Param(value = "username") String username);

    /**
     * 리포지토리에 쿼리 메서드 작성하기
     */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param(value = "username") String username, @Param(value = "age") int age);

    /**
     * 쿼리 값, Dto 조회하기
     */
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new jpa.springDataJpaStudy.datajpa.repository.dto" +
            ".MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    /**
     * 컬렉션 파라미터 바인딩
     */
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param(value = "names") Collection<String> names);

}
