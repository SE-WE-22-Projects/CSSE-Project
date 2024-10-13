package com.csse.healthSphere.model;

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

    @ManyToOne
    @JoinColumn(name = "patientId")
    Patient patient;

    // TODO: do we need to add doctorId / schedule
}
