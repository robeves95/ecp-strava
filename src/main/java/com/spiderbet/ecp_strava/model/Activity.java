package com.spiderbet.ecp_strava.model;

import com.spiderbet.ecp_strava.model.Athlete;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter
@Setter
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

    @Column(name = "sport_type", nullable = false)
    private String sportType;

    @Column(name = "distance", nullable = false)
    private double distance;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
}
