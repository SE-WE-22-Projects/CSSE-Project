package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.ReportRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.MedicalService;
import com.csse.healthSphere.model.Patient;
import com.csse.healthSphere.model.Report;
import com.csse.healthSphere.repository.AdmissionRepository;
import com.csse.healthSphere.repository.MedicalServiceRepository;
import com.csse.healthSphere.repository.PatientRepository;
import com.csse.healthSphere.repository.ReportRepository;
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

    /**
     * @param reportRequest
     * @return
     */
    public Report createReport(ReportRequest reportRequest) {
        Report report = modelMapper.map(reportRequest, Report.class);
        MedicalService medicalService = medicalServiceRepository.findById(reportRequest.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Medical Service not found"));
        report.setMedicalService(medicalService);
        report.setReportId(null);
        return reportRepository.save(report);

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
     *
     * @param id
     * @param reportRequest
     * @return
     */
    public Report updateReport(Long id, ReportRequest reportRequest) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        modelMapper.map(reportRequest,report);
        return reportRepository.save(report);
    }

    /**
     *
     * @param id
     */
    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        reportRepository.delete(report);
    }
}
