package com.spiderbet.ecp_strava.repository;

import com.spiderbet.ecp_strava.model.Athlete;
import com.spiderbet.ecp_strava.model.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> findById(Long id);

    List<Athlete> findAllByOrderByNameAsc();

    @Query("SELECT new com.spiderbet.ecp_strava.model.LeaderboardEntry(a.name, " +
            "CAST(SUM(CASE WHEN act.sportType LIKE '%Ride%' THEN act.distance * 0.25 ELSE act.distance END) AS double), " +
            "a.profilePhotoUrl) " +
            "FROM Athlete a JOIN a.activities act " +
            "WHERE act.startDate BETWEEN :startDate AND :endDate " +
            "GROUP BY a.id ORDER BY SUM(CASE WHEN act.sportType LIKE '%Ride%' THEN act.distance * 0.25 ELSE act.distance END) DESC")
    List<LeaderboardEntry> findLeaderboard(LocalDateTime startDate, LocalDateTime endDate);
}