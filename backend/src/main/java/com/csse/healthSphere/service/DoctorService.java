package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.DoctorRequest;
import com.csse.healthSphere.dto.WardAllocation;
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
     * create a new doctor
     * @param doctorRequest
     * @return
     */
    public Doctor createDoctor(DoctorRequest doctorRequest) {
        Department department = departmentRepository.findById(doctorRequest.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Ward ward = wardRepository.findById(doctorRequest.getWardId())
                .orElseThrow(() -> new ResourceNotFoundException("Ward not found"));
        Doctor doctor = modelMapper.map(doctorRequest, Doctor.class);
        doctor.setDepartment(department);
        doctor.setWard(ward);
        doctor.setPersonId(null);
        return doctorRepository.save(doctor);
    }

    /**
     * get all doctors
     * @return a list of all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * get a doctor by id
     * @param id
     * @return the doctor for the given id
     */
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    /**
     * update a doctor by id
     * @param id
     * @param doctorRequest
     * @return the updated doctor
     */
    public Doctor updateDoctor(Long id, DoctorRequest doctorRequest) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        modelMapper.map(doctorRequest, doctor);

        return doctorRepository.save(doctor);
    }

    /**
     * delete a doctor
     * @param id
     * @throws ResourceNotFoundException
     */
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        doctorRepository.delete(doctor);
    }

    /**
     * find doctors by department
     * @param departmentId
     * @return  a list of doctors
     */
    public List<Doctor> findDoctorsByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return doctorRepository.findByDepartment(department);
    }

    /**
     * find doctors by ward
     * @param wardId
     * @return a list of doctors
     */
    public List<Doctor> findDoctorsByWard(Long wardId) {
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new ResourceNotFoundException("Ward not found"));
        return doctorRepository.findByWard(ward);
    }

    /**
     * allocate a ward to a doctor
     * @param doctorId the id of the doctor
     * @param wardAllocation the ward to allocate
     * @return the updated doctor
     */
    public Doctor allocateWard(Long doctorId, WardAllocation wardAllocation) {
        Ward ward = wardRepository.findById(wardAllocation.getWardId())
                .orElseThrow(() -> new ResourceNotFoundException("Ward not found"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        doctor.setWard(ward);
        return  doctorRepository.save(doctor);
    }
}
