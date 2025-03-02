package com.spiderbet.ecp_strava.service;

import com.spiderbet.ecp_strava.model.Activity;
import com.spiderbet.ecp_strava.model.Team;
import com.spiderbet.ecp_strava.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public void createTeam(String name, String labName) {
        Team team = new Team();
        team.setName(name);
        team.setLabName(labName);
        teamRepository.save(team);
    }

    public Map<String, Double> getTeamLeaderboard(LocalDateTime startDate, LocalDateTime endDate) {
        List<Team> teams = teamRepository.findAll();
        Map<String, Double> teamDistances = new HashMap<>();

        for (Team team : teams) {
            double totalDistance = team.getAthletes().stream()
                    .flatMap(athlete -> athlete.getActivities().stream())
                    .filter(activity -> !activity.getStartDate().isBefore(startDate) && !activity.getStartDate().isAfter(endDate))
                    .mapToDouble(activity -> activity.getSportType().contains("Ride") ? activity.getDistance() * 0.25 : activity.getDistance())
                    .sum();
            teamDistances.put(team.getName(), totalDistance);
        }

        return teamDistances.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Double> getLabLeaderboard(LocalDateTime startDate, LocalDateTime endDate) {
        List<Team> teams = teamRepository.findAll();
        Map<String, Double> labDistances = new HashMap<>();

        for (Team team : teams) {
            double totalDistance = team.getAthletes().stream()
                    .flatMap(athlete -> athlete.getActivities().stream())
                    .filter(activity -> !activity.getStartDate().isBefore(startDate) && !activity.getStartDate().isAfter(endDate))
                    .mapToDouble(activity -> activity.getSportType().contains("Ride") ? activity.getDistance() * 0.25 : activity.getDistance())
                    .sum();
            labDistances.merge(team.getLabName(), totalDistance, Double::sum);
        }

        return labDistances.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

}