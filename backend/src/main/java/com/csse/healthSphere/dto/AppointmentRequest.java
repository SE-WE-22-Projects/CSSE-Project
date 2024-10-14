package com.csse.healthSphere.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
    private LocalDate date;
    private LocalTime time;
    private int queueNo;
    // private String status; // TODO: Use enum when implemented
    private long patientId;
//    private int doctorId;
}
