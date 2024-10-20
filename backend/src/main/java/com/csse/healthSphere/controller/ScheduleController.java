package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.ScheduleRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Schedule;
import com.csse.healthSphere.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/schedule")
@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * Create a new schedule
     *
     * @param scheduleRequest: the data for the new schedule
     * @return the newly created schedule
     */
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(
            @RequestBody ScheduleRequest scheduleRequest
    ) {
        Schedule createdSchedule = scheduleService.createSchedule(scheduleRequest);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    /**
     * Get all schedules
     *
     * @return a list of all schedules
     */
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    /**
     * Get a ScheduleService by id
     *
     * @param id the id of the schedule
     * @return the schedule for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(
            @PathVariable Long id
    ) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    /**
     * Update a schedule by id
     *
     * @param id              the id of the schedule
     * @param scheduleRequest the updated content of the schedule
     * @return the updated schedule
     */
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequest scheduleRequest
    ) {
        Schedule updatedSchedule = scheduleService.updateSchedule(id, scheduleRequest);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    /**
     * Delete a schedule by id
     *
     * @param id the id of the schedule
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id
    ) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get all schedules by doctor
     *
     * @param doctorId the id of the doctor
     * @return a list of all schedules for the given doctor
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Schedule>> getSchduleByDoctor(
            @PathVariable Long doctorId
    ) {
        List<Schedule> scheduleList = scheduleService.findSchedulesByDoctor(doctorId);
        return new ResponseEntity<>(scheduleList,HttpStatus.OK);
    }

    /**
     * Handle ResourceNotFoundException
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


}
