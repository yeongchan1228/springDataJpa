package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 사용자 정의 커스텀에서 클래스 이름은 스프링 데이터 JPA의 클래스 이름 + ...으로 작성해야 한다.
 * MemberRepository(스프링 데이터 JPA 인터페이스 명) + Impl
 * MemberRepositoryCustom(커스텀된 인터페이스 명) + Impl
 */
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
