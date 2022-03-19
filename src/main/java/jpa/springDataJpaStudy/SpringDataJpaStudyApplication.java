package jpa.springDataJpaStudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Auditing을 위한 설정
@SpringBootApplication
public class SpringDataJpaStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaStudyApplication.class, args);
	}

}
