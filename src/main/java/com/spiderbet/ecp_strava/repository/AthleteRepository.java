package com.spiderbet.ecp_strava.repository;

import com.spiderbet.ecp_strava.model.Athlete;
import com.spiderbet.ecp_strava.model.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> findById(Long id);

    List<Athlete> findAllByOrderByNameAsc();

    @Query("SELECT new com.spiderbet.ecp_strava.model.LeaderboardEntry(a.name, SUM(act.distance), a.profilePhotoUrl) FROM Athlete a JOIN a.activities act GROUP BY a.id ORDER BY SUM(act.distance) DESC")
    List<LeaderboardEntry> findLeaderboard();}