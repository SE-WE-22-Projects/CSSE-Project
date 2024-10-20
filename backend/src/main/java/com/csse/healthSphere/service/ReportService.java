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
     * @param reportRequest
     * @return
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
     * @return
     */
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report not found"));
    }

    /**
     * @param id
     * @param reportRequest
     * @return
     */
    public Report updateReport(Long id, ReportRequest reportRequest) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        modelMapper.map(reportRequest, report);
        return reportRepository.save(report);
    }

    /**
     * @param id
     */
    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        reportRepository.delete(report);
    }

    /**
     * @param admissionId
     * @param medicalServiceId
     * @return
     */
    public List<Report> findReportsByAdmissionAndMedicalService(Long admissionId, Long medicalServiceId) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found"));
        MedicalService medicalService = medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical service not found"));
        return reportRepository.findByAdmissionAndMedicalService(admission, medicalService);
    }

    /**
     * @param patientId
     * @param medicalServiceId
     * @return
     */
    public List<Report> findReportsByPatientAndMedicalService(Long patientId, Long medicalServiceId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        MedicalService medicalService = medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical service not found"));
        return reportRepository.findByAdmissionAppointmentPatientAndMedicalService(patient, medicalService);
    }

    /**
     * @param admissionId
     * @return
     */
    public List<Report> findReportsByAdmission(Long admissionId) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission not found"));
        return reportRepository.findByAdmission(admission);
    }

    /**
     * @param patientId
     * @return
     */
    public List<Report> findReportsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return reportRepository.findByAdmissionAppointmentPatient(patient);
    }
}
