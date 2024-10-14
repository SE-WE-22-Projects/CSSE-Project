package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.ReportRequest;
import com.csse.healthSphere.model.Report;
import com.csse.healthSphere.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public Report createReport(ReportRequest reportRequest){
        return null;
    }

    public List<Report> getAllReports(){
        return List.of();
    }

    public Report getReportById(Long id){
        return null;
    }

    public Report updateReport(Long id, ReportRequest reportRequest){
        return null;
    }

    public void deleteReport(Long id){

    }
}
