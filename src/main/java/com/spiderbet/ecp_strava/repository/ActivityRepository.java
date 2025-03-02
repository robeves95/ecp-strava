package com.spiderbet.ecp_strava.repository;

import com.spiderbet.ecp_strava.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByAthleteId(Long athleteId);
}
