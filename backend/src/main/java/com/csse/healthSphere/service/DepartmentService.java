package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.DepartmentRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Department;
import com.csse.healthSphere.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    /**
     * Create a new department
     * @param departmentRequest
     * @return the newly created department
     */
    public Department createDepartment(DepartmentRequest departmentRequest) {
        Department department = modelMapper.map(departmentRequest, Department.class);
        department.setDepartmentId(null);
        return departmentRepository.save(department);
    }

    /**
     * Get all departments
     * @return a list of all departments
     */
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * Get a department by id
     * @param id
     * @return the department for the given id
     */
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }

    /**
     * Update a department
     * @param id
     * @param departmentRequest
     * @return the updated department
     */
    public Department updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        modelMapper.map(departmentRequest, department);
        return departmentRepository.save(department);
    }

    /**
     * Delete a department
     * @param id
     */
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        departmentRepository.delete(department);
    }
}
