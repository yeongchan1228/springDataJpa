package jpa.springDataJpaStudy.datajpa.controller;

import jpa.springDataJpaStudy.datajpa.domain.Member;
import jpa.springDataJpaStudy.datajpa.repository.MemberRepository;
import jpa.springDataJpaStudy.datajpa.repository.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable(value = "id") Long id){
        Member findMember = memberRepository.findById(id).get();
        return findMember.getUsername();
    }

    /**
     * 도메인 클래스 컨버터 사용, 스프링 데이터 JPA가 자동으로 쿼리를 날려 도메인 객체를 반환
     * 트랜 잭션이 없는 범위에서 엔티티를 조회했기 때문에 단순 조회용으로 사용해야 한다.
     */
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable(value = "id") Member member){
        return member.getUsername();
    }

    /**
     * 페이징과 정렬
     * ?page=2&size=3 등으로 조절 가능하다.
     * @PageableDefault로 디폴트 값 변경 가능
     */
    @GetMapping("/members")
    public Page<MemberDto > list(@PageableDefault(size = 5, page = 1, sort = "username", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Member> all = memberRepository.findAll(pageable);
        Page<MemberDto> map = all.map(m -> new MemberDto(m.getId(), m.getUsername(), null));
        return map;
    }

//    @PostConstruct
//    public void init(){
//        for (int i = 0; i < 100; i++) {
//            memberRepository.save(new Member("member" + i));
//        }
//    }
}
