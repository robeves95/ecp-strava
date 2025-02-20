package com.spiderbet.ecp_strava.repository;

import com.spiderbet.ecp_strava.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> findByStravaAthleteId(Long stravaAthleteId);
    List<Athlete> findAllByOrderByNameAsc();
}
