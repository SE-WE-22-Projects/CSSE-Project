package com.csse.healthSphere.service;

import com.csse.healthSphere.enums.WeekDay;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.model.Schedule;
import com.csse.healthSphere.dto.ScheduleRequest;
import com.csse.healthSphere.repository.DoctorRepository;
import com.csse.healthSphere.repository.ScheduleRepository;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a schedule from the given data
     *
     * @param scheduleRequest the data for the new schedule
     * @return the created schedule
     */
    public Schedule createSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = modelMapper.map(scheduleRequest, Schedule.class);
        Doctor doctor = doctorRepository.findById(scheduleRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        schedule.setScheduleId(null);
        return scheduleRepository.save(schedule);
    }

    /**
     * Gets a list of all existing schedules.
     *
     * @return a list of schedules
     */
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    /**
     * Gets the schedule with the given id.
     *
     * @param id the id of the schedule
     * @return the schedule
     * @throws ResourceNotFoundException if the schedule does not exist
     */
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    /**
     * Updates the schedule content
     *
     * @param id              the id of the schedule
     * @param scheduleRequest the new data for the schedule
     * @return the updated schedule
     */
    public Schedule updateSchedule(Long id, ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        Doctor doctor = doctorRepository.findById(scheduleRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        modelMapper.map(scheduleRequest, schedule);
        schedule.setDoctor(doctor);
        return scheduleRepository.save(schedule);
    }

    /**
     * Deletes a schedule
     *
     * @param id the id of the schedule
     */
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        scheduleRepository.delete(schedule);
    }

    /**
     * @param doctorId
     * @return
     */
    public List<Schedule> findSchedulesByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return scheduleRepository.findByDoctor(doctor);
    }

    /**
     * @param day
     * @return
     */
    public List<Schedule> findScheduleByDay(WeekDay day) {
        return scheduleRepository.findByDay(day);
    }

    /**
     * @param doctorId
     * @param day
     * @return
     */
    public List<Schedule> findByDoctorAndDay(Long doctorId, WeekDay day) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        return scheduleRepository.findByDoctorAndDay(doctor, day);
    }
}
