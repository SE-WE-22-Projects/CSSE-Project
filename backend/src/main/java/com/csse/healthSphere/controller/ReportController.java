package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.ReportRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Report;
import com.csse.healthSphere.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/report")
@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    /**
     * Create a new report
     *
     * @param reportRequest: the data for the new report
     * @return the newly created report
     */
    @PostMapping
    public ResponseEntity<Report> createService(
            @RequestBody ReportRequest reportRequest
    ) {
        Report createdReport = reportService.createReport(reportRequest);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    /**
     * Get all reports
     *
     * @return a list of all reports
     */
    @GetMapping
    public ResponseEntity<List<Report>> getAllServices() {
        List<Report> reportList = reportService.getAllReports();
        return new ResponseEntity<>(reportList, HttpStatus.OK);
    }

    /**
     * Get a ReportService by id
     *
     * @param id the id of the report
     * @return the report for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Report> getServiceById(
            @PathVariable Long id
    ) {
        Report report = reportService.getReportById(id);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    /**
     * Update a report by id
     *
     * @param id            the id of the report
     * @param reportRequest the updated content of the report
     * @return the updated report
     */
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateService(
            @PathVariable Long id,
            @RequestBody ReportRequest reportRequest
    ) {
        Report updatedReport = reportService.updateReport(id, reportRequest);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    /**
     * Delete a report by id
     *
     * @param id the id of the report
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long id
    ) {
        reportService.deleteReport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handle ResourceNotFoundException
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


}
