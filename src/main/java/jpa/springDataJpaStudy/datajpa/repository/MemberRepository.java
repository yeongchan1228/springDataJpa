package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Member;
import jpa.springDataJpaStudy.datajpa.repository.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

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

    /**
     * 반환 타입
     * find~~~~ByUsername 가능
     */
    List<Member> findListByUsername(String username); // 리스트 조회
    Member findMemberByUsername(String username); // 단건 조회
    Optional<Member> findOptionalByUsername(String username); // Optional 단건 조회

    /**
     * paging, slice
     * Page는 Total count도 같이 받아온다, Total count까지 같이 받아오는 거라 성능이 느리다.
     * Slice는 Total count X, limit 보다 한 개 더 가져 온다. -> 실제 결과 리스트에는 없다.
     */
    List<Member> findTop3ByAge(int age);
    List<Member> findListByAge(int age, Pageable pageable);
    //일반 쿼리와 카운트 쿼리 분리, 분리하지 않으면 카운트 쿼리도 조인이 발생한다.
//    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
    @Query(value = "select m from Member m") // 카운트 쿼리와 매칭되서 카운트 쿼리로 사용된다.
    Page<Member> findByAge(int age, Pageable pageable);
    Slice<Member> findSliceByAge(int age, Pageable pageable);

    /**
     * 벌크 수정 쿼리
     */
//    @Modifying // 이게 있어야 .getResultList()가 아닌 .executeUpdate() 등 다른 메서드가 실행됨
    @Modifying(clearAutomatically = true) // 영속성 컨텍스트를 자동으로 비워줌
    @Query(value = "update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param(value = "age") int age);

    /**
     * 엔티티 그래프 사용, findALl은 이미 있는 메서드 이므로 오버라이드 필수
     */
    @Override
    @EntityGraph(attributePaths = {"team"}) // 즉시 로딩으로 바꿔주는 역할
    List<Member> findAll();

    // JPQL로 구현
    @Query("select m from Member m join fetch m.team t")
    List<Member> findMemberFetchJoin();

    @Query("select m from Member m")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(String username);

    @EntityGraph("Member.all")
    List<Member> findEntityGraph2ByUsername(String username);

    /**
     * queryHint
     */
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) // 오직 읽는 것으로 조회, 변경 감지 X
    Member findReadOnlyByUsername(String username);

    /**
     * Lock
     */
    @Lock(value = LockModeType.PESSIMISTIC_WRITE) // 동시성 제어를 위한 쓰기 락
    List<Member> findLockByUsername(String username);

    /**
     * Projections
     */
    List<UsernameOnly> findProjectionsByUsername(String username);
    List<UsernameOnlyDto> findProjectionsDtoByUsername(String username);
    // 위 메서드 2개를 합친 것
    <T> List<T> findProjectionsAllByUsername(String username, Class<T> type);

    /**
     * Native Query
     */
    @Query(value = "select * from Member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);

    @Query(value = "select m.member_id as id, m.username, t.name as teamName " +
            "from Member m left join team t",
            countQuery = "select count(*) from member",
            nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);
}
