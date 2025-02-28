package com.spiderbet.ecp_strava.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "athletes")
@Getter
@Setter
public class Athlete {

    @Id
    private Long id;

    private String name;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expires_at", nullable = false)
    private Long expiresAt;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> activities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Transient
    private Double totalDistance;

    // getters and setters for totalDistance
    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

}