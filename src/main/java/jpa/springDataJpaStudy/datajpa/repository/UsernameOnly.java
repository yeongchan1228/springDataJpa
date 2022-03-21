package jpa.springDataJpaStudy.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

//    @Value("#{target.username + ' ' + target.age}") // 오픈 프로젝션 -> 다 가져와서 username과 age만
    String getUsername(); // 클로즈 프로젝션 -> username만 가져옴
}
