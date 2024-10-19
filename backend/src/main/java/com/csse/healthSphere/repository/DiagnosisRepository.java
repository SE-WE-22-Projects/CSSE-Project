package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Appointment;
import com.csse.healthSphere.model.Diagnosis;
import com.csse.healthSphere.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
