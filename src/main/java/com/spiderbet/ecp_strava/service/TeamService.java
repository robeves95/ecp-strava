package com.spiderbet.ecp_strava.service;

import com.spiderbet.ecp_strava.model.Team;
import com.spiderbet.ecp_strava.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}