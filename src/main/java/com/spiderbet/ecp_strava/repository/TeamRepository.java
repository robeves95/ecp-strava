package com.spiderbet.ecp_strava.repository;

import com.spiderbet.ecp_strava.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
}