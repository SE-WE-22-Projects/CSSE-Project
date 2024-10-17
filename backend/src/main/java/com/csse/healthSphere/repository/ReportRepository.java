package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Admission;
import com.csse.healthSphere.model.MedicalService;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    /**
     * get list of reports according to admission and service
     * @param admission
     * @param medicalService
     * @return
     */
    List<Report> findByAdmissionAndMedicalService(Admission admission, MedicalService medicalService);

    /**
     * get list of reports according to patient and service
     * @param patient
     * @param medicalService
     * @return
     */
    List<Report> findByAdmissionAppointmentPatientAndMedicalService(Patient patient, MedicalService medicalService);

    /**
     * get list of reports according to admission
     * @param admission
     * @return
     */
    List<Report> findByAdmission(Admission admission);

    /**
     * get list of reports according to patient
     * @param patient
     * @return
     */
    List<Report> findByAdmissionAppointmentPatient(Patient patient);
}
