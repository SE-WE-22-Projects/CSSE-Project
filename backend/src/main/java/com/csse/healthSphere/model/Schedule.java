package com.csse.healthSphere.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long scheduleId;

    String scheduleName;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    Doctor doctor;

    LocalTime startTime;
    LocalTime endTime;
}
