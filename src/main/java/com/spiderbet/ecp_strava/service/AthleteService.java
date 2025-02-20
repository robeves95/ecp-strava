package com.spiderbet.ecp_strava.service;

import com.spiderbet.ecp_strava.model.Athlete;
import com.spiderbet.ecp_strava.model.Team;
import com.spiderbet.ecp_strava.repository.AthleteRepository;
import com.spiderbet.ecp_strava.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final TeamRepository teamRepository;

    public AthleteService(AthleteRepository athleteRepository, TeamRepository teamRepository) {
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
    }

    public void saveOrUpdateAthlete(Long stravaAthleteId, String name, String accessToken, String refreshToken, Long expiresAt, String teamName) {
        Team team = teamRepository.findByName(teamName).orElseGet(() -> {
            Team newTeam = new Team();
            newTeam.setName(teamName);
            return teamRepository.save(newTeam);
        });

        Athlete athlete = athleteRepository.findByStravaAthleteId(stravaAthleteId).orElse(new Athlete());
        athlete.setStravaAthleteId(stravaAthleteId);
        athlete.setName(name);
        athlete.setAccessToken(accessToken);
        athlete.setRefreshToken(refreshToken);
        athlete.setExpiresAt(expiresAt);
        athlete.setTeam(team);

        athleteRepository.save(athlete);
    }

    public List<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }

    public void changeTeam(Long athleteId, Long teamId) {
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow(() -> new IllegalArgumentException("Invalid athlete ID"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("Invalid team ID"));
        athlete.setTeam(team);
        athleteRepository.save(athlete);
    }

    public Map<String, Double> getLeaderboard() {
        return athleteRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Athlete::getName,
                        athlete -> athlete.getActivities().stream().mapToDouble(activity -> activity.getDistance() / 1000).sum()
                ));
    }
}