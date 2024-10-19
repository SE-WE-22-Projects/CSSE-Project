package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.WardRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Ward;
import com.csse.healthSphere.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/ward")
@RestController
@RequiredArgsConstructor
public class WardController extends BaseController {
    private final WardService wardService;

    /**
     * Create a new ward
     *
     * @param wardRequest: the data for the new ward
     * @return the newly created ward
     */
    @PostMapping
    public ResponseEntity<Ward> createWard(
            @RequestBody WardRequest wardRequest
    ) {
        Ward createdWard = wardService.createWard(wardRequest);
        return new ResponseEntity<>(createdWard, HttpStatus.CREATED);
    }

    /**
     * Get all wards
     *
     * @return a list of all wards
     */
    @GetMapping
    public ResponseEntity<List<Ward>> getAllWards() {
        List<Ward> wardList = wardService.getAllWards();
        return new ResponseEntity<>(wardList, HttpStatus.OK);
    }

    /**
     * Get a WardService by id
     *
     * @param id the id of the ward
     * @return the ward for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ward> getWardById(
            @PathVariable Long id
    ) {
        Ward ward = wardService.getWardById(id);
        return new ResponseEntity<>(ward, HttpStatus.OK);
    }

    /**
     * Update a ward by id
     *
     * @param id          the id of the ward
     * @param wardRequest the updated content of the ward
     * @return the updated ward
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ward> updateWard(
            @PathVariable Long id,
            @RequestBody WardRequest wardRequest
    ) {
        Ward updatedWard = wardService.updateWard(id, wardRequest);
        return new ResponseEntity<>(updatedWard, HttpStatus.OK);
    }

    /**
     * Delete a ward by id
     *
     * @param id the id of the ward
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWard(
            @PathVariable Long id
    ) {
        wardService.deleteWard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
