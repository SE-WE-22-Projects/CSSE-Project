package com.csse.healthSphere.model;

import com.csse.healthSphere.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    private AppointmentStatus status;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIncludeProperties({"personId", "name"})
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
