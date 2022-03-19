package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Member;

import java.util.List;

/**
 * 사용자 정의 리포지토리로 queryDSL같은 복잡한 쿼리를 구현시킬 때 유용
 * MemberRepository에서 구현하려면 모든 메서드를 다 오버라이드 해야 하므로
 */
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
