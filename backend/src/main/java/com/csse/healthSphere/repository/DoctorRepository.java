package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Department;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    /**
     * get list of doctors according to patient
     * @param department
     * @return
     */
    List<Doctor> findByDepartment(Department department);

    /**
     *  get list of doctors according to ward
     * @param ward
     * @return
     */
    List<Doctor> findByWard(Ward ward);
}
