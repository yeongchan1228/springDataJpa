package jpa.springDataJpaStudy.hello.repository;

import jpa.springDataJpaStudy.hello.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
