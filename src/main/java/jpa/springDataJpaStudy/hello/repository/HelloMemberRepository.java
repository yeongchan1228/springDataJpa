package jpa.springDataJpaStudy.hello.repository;

import jpa.springDataJpaStudy.hello.domain.HelloMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloMemberRepository extends JpaRepository<HelloMember, Long> {

}
