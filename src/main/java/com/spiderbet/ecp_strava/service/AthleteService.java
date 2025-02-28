package com.spiderbet.ecp_strava.service;

import com.spiderbet.ecp_strava.model.Athlete;
import com.spiderbet.ecp_strava.model.LeaderboardEntry;
import com.spiderbet.ecp_strava.model.Team;
import com.spiderbet.ecp_strava.repository.AthleteRepository;
import com.spiderbet.ecp_strava.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
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

    public void saveOrUpdateAthlete(Long stravaAthleteId, String name, String accessToken, String refreshToken, Long expiresAt, String profilePhotoUrl, Long teamId) {
        Athlete athlete = athleteRepository.findById(stravaAthleteId).orElse(new Athlete());
        athlete.setId(stravaAthleteId);
        athlete.setName(name);
        athlete.setAccessToken(accessToken);
        athlete.setRefreshToken(refreshToken);
        athlete.setExpiresAt(expiresAt);
        athlete.setProfilePhotoUrl(profilePhotoUrl);

        if (teamId != null) {
            Team team = teamRepository.findById(teamId).orElse(null);
            athlete.setTeam(team);
        } else {
            athlete.setTeam(null);
        }

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

    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> results = athleteRepository.findLeaderboard();
        return results;
    }
}