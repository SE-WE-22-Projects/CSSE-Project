package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Charge;
import com.csse.healthSphere.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {
    /**
     * get list of charges according to appointment
     * @param appointment
     * @return
     */
    List<Charge> findByAppointment(Appointment appointment);

    /**
     * get list of charges according to patient
     * @param patient
     * @return
     */
    List<Charge> findByAppointmentPatient(Patient patient);

    /**
     * sum of charge according to appointment id
     * @param appointment
     * @return
     */
    @Query("SELECT SUM(c.amount) FROM Charge c WHERE c.appointment = :appointment")
    Float getTotalAmount(@Param("appointment") Appointment appointment);
}
