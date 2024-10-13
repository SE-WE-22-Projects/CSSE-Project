package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
