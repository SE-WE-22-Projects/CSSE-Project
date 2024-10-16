package com.csse.healthSphere.model;

import com.csse.healthSphere.enums.WeekDay;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private WeekDay day;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
