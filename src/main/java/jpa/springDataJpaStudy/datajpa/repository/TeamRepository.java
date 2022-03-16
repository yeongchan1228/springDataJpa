package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
