package com.spiderbet.ecp_strava.model;

public class LeaderboardEntry {
    private String name;
    private Double totalDistance;
    private String profilePhotoUrl;

    public LeaderboardEntry(String name, Double totalDistance, String profilePhotoUrl) {
        this.name = name;
        this.totalDistance = totalDistance;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }
}