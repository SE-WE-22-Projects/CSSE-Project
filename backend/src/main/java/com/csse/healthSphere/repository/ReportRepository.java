package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<MedicalRecord, Long> {

}
