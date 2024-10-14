package com.csse.healthSphere.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
    @NonNull
    private LocalDate date;
    @NonNull
    private LocalTime time;
    @NonNull
    private Integer queueNo;
    // private String status; // TODO: Use enum when implemented
    private Long patientId;
//    private int doctorId;
}
