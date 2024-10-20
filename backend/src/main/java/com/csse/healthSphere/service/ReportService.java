package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.ReportRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.*;
import com.csse.healthSphere.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final PatientRepository patientRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final AdmissionRepository admissionRepository;
    private final ModelMapper modelMapper;
    private final ChargeRepository chargeRepository;

    /**
     * create a new report
     * @param reportRequest
     * @return the newly created report
     */
    public Report createReport(ReportRequest reportRequest) {
        Report report = modelMapper.map(reportRequest, Report.class);
        MedicalService medicalService = medicalServiceRepository.findById(reportRequest.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Medical Service not found"));


        Admission admission = admissionRepository.findById(reportRequest.getAdmissionId())
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found"));
        report = reportRepository.save(report);

        ServiceCharge charge = new ServiceCharge();
        charge.setAmount(medicalService.getPrice());
        charge.setReport(report);
        charge.setAppointment(admission.getAppointment());
        chargeRepository.save(charge);

        report.setAdmission(admission);
        report.setMedicalService(medicalService);
        return report;
    }

    /**
     * get all reports
     * @return a list of all reports
     */
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    /**
     * get a report by id
     * @param id
     * @return the report for the given id
     */
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report not found"));
    }

    /**
     * update a report
     * @param id
     * @param reportRequest
     * @return the updated report
     */
    public Report updateReport(Long id, ReportRequest reportRequest) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        modelMapper.map(reportRequest, report);
        return reportRepository.save(report);
    }

    /**
     * delete a report
     * @param id
     */
    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        reportRepository.delete(report);
    }

    /**
     * get all reports for a given admission
     * @param admissionId
     * @param medicalServiceId
     * @return a list of reports for the given admission
     */
    public List<Report> findReportsByAdmissionAndMedicalService(Long admissionId, Long medicalServiceId) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found"));
        MedicalService medicalService = medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical service not found"));
        return reportRepository.findByAdmissionAndMedicalService(admission, medicalService);
    }

    /**
     * find reports by patient and medical service
     * @param patientId
     * @param medicalServiceId
     * @return a list of reports for the given patient and medical service
     */
    public List<Report> findReportsByPatientAndMedicalService(Long patientId, Long medicalServiceId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        MedicalService medicalService = medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical service not found"));
        return reportRepository.findByAdmissionAppointmentPatientAndMedicalService(patient, medicalService);
    }

    /**
     * find reports by admission
     * @param admissionId
     * @return a list of reports for the given admission
     */
    public List<Report> findReportsByAdmission(Long admissionId) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found"));
        return reportRepository.findByAdmission(admission);
    }

    /**
     * find reports by patient
     * @param patientId
     * @return a list of reports for the given patient
     */
    public List<Report> findReportsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return reportRepository.findByAdmissionAppointmentPatient(patient);
    }
}
