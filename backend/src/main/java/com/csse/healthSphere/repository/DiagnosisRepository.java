package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Diagnosis;
import com.csse.healthSphere.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    /**
     * get diagnosis according to appointment
     * @param appointment
     * @return
     */
    Diagnosis findByAppointment(Appointment appointment);

    /**
     * get list of diagnosis according to patient
     * @param patient
     * @return
     */
    List<Diagnosis> findByPatient(Patient patient);
}
