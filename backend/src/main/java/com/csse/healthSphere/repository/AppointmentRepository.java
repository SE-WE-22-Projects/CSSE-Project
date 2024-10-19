package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    /**
     * get list of appointments according to patient
     * @param patient
     * @return
     */
    List<Appointment> findByPatient(Patient patient);

    /**
     * get list of appointments according to schedule
     * @param schedule
     * @return
     */
    List<Appointment> findBySchedule(Schedule schedule);

    /**
     * get list of appointments according to doctor
     * @param doctor
     * @return
     */
    List<Appointment> findByScheduleDoctor(Doctor doctor);

    /**
     * get maximum queue number for schedule of given date
     * @param scheduleId
     * @param date
     * @return
     */
    @Query("SELECT COALESCE(MAX(a.queueNo), 0) FROM Appointment a WHERE a.schedule.scheduleId = :scheduleId AND a.date = :date")
    int findMaxQueueNoByScheduleAndDate(@Param("scheduleId") Long scheduleId, @Param("date") LocalDate date);
}
