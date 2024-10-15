package com.csse.healthSphere.repository;

import com.csse.healthSphere.enums.WeekDay;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /**
     * get list of schedules according to doctor
     * @param doctor
     * @return
     */
    List<Schedule> findByDoctor(Doctor doctor);

    /**
     * get list of schedules according to day
     * @param day
     * @return
     */
    List<Schedule> findByDay(WeekDay day);

    /**
     * get list of schedules according to doctor and day
     * @param doctor
     * @param day
     * @return
     */
    List<Schedule> findByDoctorAndDay(Doctor doctor, WeekDay day);
}
