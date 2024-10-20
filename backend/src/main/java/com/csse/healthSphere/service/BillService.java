package com.csse.healthSphere.service;

import com.csse.healthSphere.model.Bill;
import com.csse.healthSphere.dto.BillRequest;
import com.csse.healthSphere.repository.BillRepository;
import com.csse.healthSphere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private ModelMapper modelMapper;

    /**
     * Creates a bill with the given data
     *
     * @param billRequest the data for the new bill
     * @return the created bill
     */
    public Bill createBill(BillRequest billRequest) {
        return null;
    }

    /**
     * Gets a list of all existing bills.
     * 
     * @return a list of bills
     */
    public List<Bill> getAllBills() {
        return List.of();
    }

    /**
     * Gets the bill with the given id.
     *
     * @param id the id of the bill
     * @return the bill
     * @throws ResourceNotFoundException if the bill does not exist
     */
    public Bill getBillById(Long id) {
        return null;
    }

    /**
     * Updates the bill content
     *
     * @param id          the id of the bill
     * @param billRequest the new data for the bill
     * @return the updated bill
     */
    public Bill updateBill(Long id, BillRequest billRequest) {
        return null;
    }

    /**
     * Deletes a bill
     *
     * @param id the id of the bill
     */
    public void deleteBill(Long id) {

    }
}
