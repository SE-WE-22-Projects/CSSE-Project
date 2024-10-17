package com.csse.healthSphere.service;

import com.csse.healthSphere.model.Schedule;
import com.csse.healthSphere.dto.ScheduleRequest;
import com.csse.healthSphere.repository.ScheduleRepository;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private ModelMapper modelMapper;

    /**
     * Creates a schedule from the given data
     *
     * @param scheduleRequest the data for the new schedule
     * @return the created schedule
     */
    public Schedule createSchedule(ScheduleRequest scheduleRequest) {
        return null;
    }

    /**
     * Gets a list of all existing schedules.
     *
     * @return a list of schedules
     */
    public List<Schedule> getAllSchedules() {
        return List.of();
    }

    /**
     * Gets the schedule with the given id.
     *
     * @param id the id of the schedule
     * @return the schedule
     * @throws ResourceNotFoundException if the schedule does not exist
     */
    public Schedule getScheduleById(Long id) {
        return null;
    }

    /**
     * Updates the schedule content
     *
     * @param id              the id of the schedule
     * @param scheduleRequest the new data for the schedule
     * @return the updated schedule
     */
    public Schedule updateSchedule(Long id, ScheduleRequest scheduleRequest) {
        return null;
    }

    /**
     * Deletes a schedule
     *
     * @param id the id of the schedule
     */
    public void deleteSchedule(Long id) {

    }
}
