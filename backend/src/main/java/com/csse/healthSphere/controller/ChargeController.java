package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.ChargeRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Charge;
import com.csse.healthSphere.service.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/charge")
@RestController
@RequiredArgsConstructor
public class ChargeController {
    private final ChargeService chargeService;

    /**
     * Create a new charge
     *
     * @param chargeRequest: the data for the new charge
     * @return the newly created charge
     */
    @PostMapping
    public ResponseEntity<Charge> createCharge(
            @RequestBody ChargeRequest chargeRequest
    ) {
        Charge createdCharge = chargeService.createCharge(chargeRequest);
        return new ResponseEntity<>(createdCharge, HttpStatus.CREATED);
    }

    /**
     * Get all charges
     *
     * @return a list of all charges
     */
    @GetMapping
    public ResponseEntity<List<Charge>> getAllCharges() {
        List<Charge> chargeList = chargeService.getAllCharges();
        return new ResponseEntity<>(chargeList, HttpStatus.OK);
    }

    /**
     * Get a ChargeService by id
     *
     * @param id the id of the charge
     * @return the charge for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Charge> getChargeById(
            @PathVariable Long id
    ) {
        Charge charge = chargeService.getChargeById(id);
        return new ResponseEntity<>(charge, HttpStatus.OK);
    }

    /**
     * Update a charge by id
     *
     * @param id            the id of the charge
     * @param chargeRequest the updated content of the charge
     * @return the updated charge
     */
    @PutMapping("/{id}")
    public ResponseEntity<Charge> updateChargeById(
            @PathVariable Long id,
            @RequestBody ChargeRequest chargeRequest
    ) {
        Charge updatedCharge = chargeService.updateCharge(id, chargeRequest);
        return new ResponseEntity<>(updatedCharge, HttpStatus.OK);
    }

    /**
     * Delete a charge by id
     *
     * @param id the id of the charge
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChargeById(
            @PathVariable Long id
    ) {
        chargeService.deleteCharge(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
