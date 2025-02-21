package com.spiderbet.ecp_strava.controller;

import com.spiderbet.ecp_strava.service.AthleteService;
import com.spiderbet.ecp_strava.service.TeamService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class StravaController {

    @Value("${strava.client.id}")
    private String clientId;

    @Value("${strava.client.secret}")
    private String clientSecret;

    @Value("${strava.redirect.uri}")
    private String redirectUri;

    private final AthleteService athleteService;
    private final TeamService teamService;
    private final RestTemplate restTemplate = new RestTemplate();

    public StravaController(AthleteService athleteService, TeamService teamService) {
        this.athleteService = athleteService;
        this.teamService = teamService;
    }

    private static final String STRAVA_AUTH_URL = "https://www.strava.com/oauth/authorize";
    private static final String STRAVA_TOKEN_URL = "https://www.strava.com/oauth/token";

    @GetMapping("/login")
    public RedirectView loginToStrava() {
        String authUrl = STRAVA_AUTH_URL + "?client_id=" + clientId +
                "&response_type=code&redirect_uri=" + redirectUri +
                "&approval_prompt=force&scope=read,activity:read_all";
        return new RedirectView(authUrl);
    }

    @GetMapping("/callback")
    public String handleCallback(@RequestParam(name = "code", required = false) String code,
                                 @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            return "error"; // Redirect to an error page if authentication fails
        }

        // Exchange the authorization code for an access token
        ResponseEntity<Map> response = requestAccessToken(code);
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            assert responseBody != null;

            String accessToken = (String) responseBody.get("access_token");
            String refreshToken = (String) responseBody.get("refresh_token");
            Long expiresAt = ((Number) responseBody.get("expires_at")).longValue();
            Map<String, Object> athleteData = (Map<String, Object>) responseBody.get("athlete");
            Long stravaAthleteId = ((Number) athleteData.get("id")).longValue();
            String name = athleteData.get("firstname") + " " + athleteData.get("lastname");
            String profilePhotoUrl = (String) athleteData.get("profile");

            // Save athlete to the database without setting the team
            athleteService.saveOrUpdateAthlete(stravaAthleteId, name, accessToken, refreshToken, expiresAt, profilePhotoUrl, null);

            return "redirect:/"; // Redirect to dashboard with query parameter
        }

        return "error"; // Redirect to an error page if token exchange fails
    }

    private ResponseEntity<Map> requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code +
                "&grant_type=authorization_code";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(STRAVA_TOKEN_URL, HttpMethod.POST, entity, Map.class);
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("athletes", athleteService.getAllAthletes());
        model.addAttribute("teams", teamService.getAllTeams());
        model.addAttribute("leaderboard", athleteService.getLeaderboard());
        model.addAttribute("teamLeaderboard", teamService.getTeamLeaderboard());
        model.addAttribute("labLeaderboard", teamService.getLabLeaderboard());

        return "dashboard";
    }

    @PostMapping("/athletes/changeTeam")
    public String changeTeam(@RequestParam Long athleteId, @RequestParam Long teamId) {
        athleteService.changeTeam(athleteId, teamId);
        return "redirect:/";
    }

    @PostMapping("/teams/create")
    public String createTeam(@RequestParam String name, @RequestParam String labName) {
        teamService.createTeam(name, labName);
        return "redirect:/";
    }
}