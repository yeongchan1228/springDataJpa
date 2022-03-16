package jpa.springDataJpaStudy.hello.repository;

import jpa.springDataJpaStudy.hello.domain.HelloMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false) // false로 적용해 데이터 베이스 초기화 방지
class HelloMemberRepositoryTest {

    @Autowired
    HelloMemberRepository memberRepository;

    @Test
    void memberTest(){ // springDataJpa 테스트
        HelloMember member = new HelloMember("memberA");

        HelloMember saveMember = memberRepository.save(member);
        HelloMember findMember = memberRepository.findById(saveMember.getId()).get();

        assertThat(saveMember.getId()).isEqualTo(findMember.getId());
        assertThat(saveMember.getUsername()).isEqualTo(findMember.getUsername());
        assertThat(findMember).isEqualTo(member);

    }
}