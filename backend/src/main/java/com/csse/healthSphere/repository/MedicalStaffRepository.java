package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.MedicalStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalStaffRepository extends JpaRepository<MedicalStaff, Long> {

    Optional<MedicalStaff> findByEmail(String email);
}
