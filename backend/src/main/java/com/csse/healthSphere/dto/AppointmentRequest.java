package com.csse.healthSphere.dto;

import com.csse.healthSphere.enums.AppointmentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
    private LocalDate date;
    private LocalTime time;
    private int queueNo;
    private AppointmentStatus status;
    private long patientId;
    private long scheduleId;
}
