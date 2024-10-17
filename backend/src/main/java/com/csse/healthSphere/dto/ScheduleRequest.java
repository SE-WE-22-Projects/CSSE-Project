package com.csse.healthSphere.dto;

import com.csse.healthSphere.enums.WeekDay;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleRequest {
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private WeekDay day;
    private Long doctorId;
}
