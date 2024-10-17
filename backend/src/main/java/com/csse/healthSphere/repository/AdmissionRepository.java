package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Admission;
import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    /**
     * get admission according to appointment
     * @param appointment
     * @return
     */
    Admission findByAppointment(Appointment appointment);

    /**
     * get list of appointments according to patient
     * @param patient
     * @return
     */
    List<Admission> findByAppointmentPatient(Patient patient);
}
