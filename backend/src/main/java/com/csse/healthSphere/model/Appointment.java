package com.csse.healthSphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int appointmentId;

    LocalDate date;
    LocalTime time;
    int queueNo;
    String status; // TODO: Need to add enum

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patientId")
    Patient patient;

    // TODO: do we need to add doctorId / schedule
}
