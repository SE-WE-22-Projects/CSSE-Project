package com.csse.healthSphere.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentCreation {
    private LocalDate date;
    private long scheduleId;
}
