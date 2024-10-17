package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.DepartmentRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Department;
import com.csse.healthSphere.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/department")
@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    /**
     * Create a new department
     *
     * @param departmentRequest: the data for the new department
     * @return the newly created department
     */
    @PostMapping
    public ResponseEntity<Department> createService(
            @RequestBody DepartmentRequest departmentRequest
    ) {
        Department createdDepartment = departmentService.createDepartment(departmentRequest);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    /**
     * Get all departments
     *
     * @return a list of all departments
     */
    @GetMapping
    public ResponseEntity<List<Department>> getAllServices() {
        List<Department> departmentList = departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    /**
     * Get a DepartmentService by id
     *
     * @param id the id of the department
     * @return the department for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getServiceById(
            @PathVariable Long id
    ) {
        Department department = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * Update a department by id
     *
     * @param id                the id of the department
     * @param departmentRequest the updated content of the department
     * @return the updated department
     */
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateService(
            @PathVariable Long id,
            @RequestBody DepartmentRequest departmentRequest
    ) {
        Department updatedDepartment = departmentService.updateDepartment(id, departmentRequest);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    /**
     * Delete a department by id
     *
     * @param id the id of the department
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long id
    ) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handle ResourceNotFoundException
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


}
