package com.csse.healthSphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * - bill: Bill
 * - schedule: Schedule
 */

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    private LocalDate date;
    private LocalTime time;
    private int queueNo;
    private String status; // TODO: Need to add enum


    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIncludeProperties({"personId", "name"})
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
