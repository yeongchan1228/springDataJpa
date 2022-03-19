package jpa.springDataJpaStudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing // Auditing을 위한 설정
@SpringBootApplication
public class SpringDataJpaStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaStudyApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider(){ // 등록자, 수정자를 넣기 위해 필요한 메서드
		// 스프링 시큐리티를 사용하면 거기서 세션에서 정보를 꺼내와 넣어줘야 한다.
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
