package com.spiderbet.ecp_strava.repository;

import com.spiderbet.ecp_strava.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> findById(Long id);
    List<Athlete> findAllByOrderByNameAsc();
    @Query("SELECT a.name, SUM(act.distance) as totalDistance FROM Athlete a JOIN a.activities act GROUP BY a.id ORDER BY totalDistance DESC")
    List<Object[]> findLeaderboard();


}
