package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
