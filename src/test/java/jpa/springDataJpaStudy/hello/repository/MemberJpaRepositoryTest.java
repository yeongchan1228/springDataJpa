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
@Rollback(value = false)
public class MemberJpaRepositoryTest {

    @Autowired
    HelloMemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        HelloMember member = new HelloMember("memberA");

        HelloMember savedMember = memberJpaRepository.save(member);
        HelloMember findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장
    } }
