package com.csse.healthSphere.controllerTest;

import com.csse.healthSphere.controller.BaseController;
import com.csse.healthSphere.controller.ReportController;
import com.csse.healthSphere.dto.ReportRequest;
import com.csse.healthSphere.enums.ReportStatus;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Report;
import com.csse.healthSphere.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).setControllerAdvice(new BaseController()).build();
    }

    // Test for creating a report - positive case
    @Test
    void createReport_PositiveTest() throws Exception {
        ReportRequest request = new ReportRequest();
        request.setResult("Test Result");
        request.setStatus("Completed");
        request.setServiceId(1L);

        Report createdReport = new Report();
        createdReport.setReportId(1L);
        createdReport.setResult("Test Result");
        createdReport.setStatus(ReportStatus.PENDING); // Assuming ReportStatus is an enum

        when(reportService.createReport(any(ReportRequest.class))).thenReturn(createdReport);

        mockMvc.perform(post("/api/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reportId").value(1L))
                .andExpect(jsonPath("$.result").value("Test Result"));
    }

    // Test for getting all reports - positive case
    @Test
    void getAllReports_PositiveTest() throws Exception {
        List<Report> reportList = List.of(new Report());

        when(reportService.getAllReports()).thenReturn(reportList);

        mockMvc.perform(get("/api/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for getting all reports - negative case (empty list)
    @Test
    void getAllReports_NegativeTest_EmptyList() throws Exception {
        when(reportService.getAllReports()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for getting a report by id - positive case
    @Test
    void getReportById_PositiveTest() throws Exception {
        Report report = new Report();
        report.setReportId(1L);
        report.setResult("Test Result");
        report.setStatus(ReportStatus.PENDING); // Assuming ReportStatus is an enum

        when(reportService.getReportById(1L)).thenReturn(report);

        mockMvc.perform(get("/api/report/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportId").value(1L))
                .andExpect(jsonPath("$.result").value("Test Result"));
    }

    // Test for getting a report by id - negative case (not found)
    @Test
    void getReportById_NegativeTest_NotFound() throws Exception {
        when(reportService.getReportById(1L)).thenThrow(new ResourceNotFoundException("Report not found"));

        mockMvc.perform(get("/api/report/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    // Test for updating a report - positive case
    @Test
    void updateReport_PositiveTest() throws Exception {
        ReportRequest request = new ReportRequest();
        request.setResult("Updated Result");
        request.setStatus("In Progress");
        request.setServiceId(1L);

        Report updatedReport = new Report();
        updatedReport.setReportId(1L);
        updatedReport.setResult("Updated Result");

        when(reportService.updateReport(anyLong(), any(ReportRequest.class))).thenReturn(updatedReport);

        mockMvc.perform(put("/api/report/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportId").value(1L))
                .andExpect(jsonPath("$.result").value("Updated Result"));
    }

    // Test for updating a report - negative case (not found)
    @Test
    void updateReport_NegativeTest_NotFound() throws Exception {
        ReportRequest request = new ReportRequest();
        request.setResult("Some Result");
        request.setStatus("Pending");

        when(reportService.updateReport(anyLong(), any(ReportRequest.class)))
                .thenThrow(new ResourceNotFoundException("Report not found"));

        mockMvc.perform(put("/api/report/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    // Test for deleting a report - positive case
    @Test
    void deleteReport_PositiveTest() throws Exception {
        mockMvc.perform(delete("/api/report/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(reportService, times(1)).deleteReport(1L);
    }

    // Test for deleting a report - negative case (not found)
    @Test
    void deleteReport_NegativeTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Report not found")).when(reportService).deleteReport(100L);

        mockMvc.perform(delete("/api/report/{id}", 100L))
                .andExpect(status().isNotFound());
    }

    // Test for finding reports by admission and medical service - positive case
    @Test
    void findReportsByAdmissionAndMedicalService_PositiveTest() throws Exception {
        List<Report> reportList = List.of(new Report());

        when(reportService.findReportsByAdmissionAndMedicalService(1L, 1L)).thenReturn(reportList);

        mockMvc.perform(get("/api/report/admission/medicalservice/{admissionId}/{medicalServiceId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // Test for finding reports by admission and medical service - negative case (not found)
    @Test
    void findReportsByAdmissionAndMedicalService_NegativeTest_NotFound() throws Exception {
        when(reportService.findReportsByAdmissionAndMedicalService(1L, 1L))
                .thenThrow(new ResourceNotFoundException("Reports not found"));

        mockMvc.perform(get("/api/report/admission/medicalservice/{admissionId}/{medicalServiceId}", 1L, 1L))
                .andExpect(status().isNotFound());
    }
}
