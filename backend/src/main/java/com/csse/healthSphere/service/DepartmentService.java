package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.DepartmentRequest;
import com.csse.healthSphere.model.Department;
import com.csse.healthSphere.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department createDepartment(DepartmentRequest departmentRequest){
        return null;
    }

    public List<Department> getAllDepartments(){
        return List.of();
    }

    public Department getDepartmentById(Long id){
        return null;
    }

    public Department updateDepartment(Long id, DepartmentRequest departmentRequest){
        return null;
    }

    public void deleteDepartment(Long id){

    }
}
