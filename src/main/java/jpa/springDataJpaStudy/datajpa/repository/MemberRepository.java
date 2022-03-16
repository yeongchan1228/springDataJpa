package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsername(String username); // 쿼리 메서드 기능 -> 이름 조회
    List<Member> findXXBy(String username); // 쿼리 메서드 기능 -> 전체 조회
    List<Member> findTop3XXBy(String username); // 쿼리 메서드 기능 -> Limit 조회 -> limit3

    /**
     * 쿼리 메서드 기능
     * 메서드 이름을 가지고 쿼리 생성
     */
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
}
