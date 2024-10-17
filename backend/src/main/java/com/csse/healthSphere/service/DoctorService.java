package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.DoctorRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Department;
import com.csse.healthSphere.model.Doctor;
import com.csse.healthSphere.model.Ward;
import com.csse.healthSphere.repository.DepartmentRepository;
import com.csse.healthSphere.repository.DoctorRepository;
import com.csse.healthSphere.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final WardRepository wardRepository;
    private final ModelMapper modelMapper;

    /**
     *
     * @param doctorRequest
     * @return
     */
    public Doctor createDoctor(DoctorRequest doctorRequest) {
        Department department = departmentRepository.findById(doctorRequest.getDepartmentId())
                .orElseThrow(()-> new ResourceNotFoundException("Department not found"));
        Ward ward = wardRepository.findById(doctorRequest.getWardId())
                .orElseThrow(()-> new ResourceNotFoundException("Ward not found"));
        Doctor doctor = modelMapper.map(doctorRequest, Doctor.class);
        doctor.setDepartment(department);
        doctor.setWard(ward);
        doctor.setPersonId(null);
        return doctorRepository.save(doctor);
    }

    /**
     *
     * @return
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    /**
     *
     * @param id
     * @param doctorRequest
     * @return
     */
    public Doctor updateDoctor(Long id, DoctorRequest doctorRequest) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        modelMapper.map(doctorRequest, doctor);

        return doctorRepository.save(doctor);
    }

    /**
     *
     * @param id
     */
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        doctorRepository.delete(doctor);
    }

    /**
     *
     * @param departmentId
     * @return
     */
    public List<Doctor> findDoctorsByDepartment(Long departmentId){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found"));
        return doctorRepository.findByDepartment(department);
    }

    /**
     *
     * @param wardId
     * @return
     */
    public List<Doctor> findDoctorsByWard(Long wardId){
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(()-> new ResourceNotFoundException("Ward not found"));
        return doctorRepository.findByWard(ward);
    }
}
