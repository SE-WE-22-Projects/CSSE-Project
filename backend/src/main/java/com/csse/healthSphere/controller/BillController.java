package com.csse.healthSphere.controller;

import com.csse.healthSphere.dto.BillRequest;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import com.csse.healthSphere.model.Bill;
import com.csse.healthSphere.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/bill")
@RestController
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    /**
     * Create a new bill
     *
     * @param billRequest: the data for the new bill
     * @return the newly created bill
     */
    @PostMapping
    public ResponseEntity<Bill> createBill(
            @RequestBody BillRequest billRequest
    ) {
        Bill createdBill = billService.createBill(billRequest);
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }

    /**
     * Get all bills
     *
     * @return a list of all bills
     */
    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> billList = billService.getAllBills();
        return new ResponseEntity<>(billList, HttpStatus.OK);
    }

    /**
     * Get a BillService by id
     *
     * @param id the id of the bill
     * @return the bill for the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(
            @PathVariable Long id
    ) {
        Bill bill = billService.getBillById(id);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    /**
     * Update a bill by id
     *
     * @param id          the id of the bill
     * @param billRequest the updated content of the bill
     * @return the updated bill
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(
            @PathVariable Long id,
            @RequestBody BillRequest billRequest
    ) {
        Bill updatedBill = billService.updateBill(id, billRequest);
        return new ResponseEntity<>(updatedBill, HttpStatus.OK);
    }

    /**
     * Delete a bill by id
     *
     * @param id the id of the bill
     * @return an empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(
            @PathVariable Long id
    ) {
        billService.deleteBill(id);
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
