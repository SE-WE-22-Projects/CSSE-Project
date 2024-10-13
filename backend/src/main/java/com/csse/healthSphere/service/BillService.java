package com.csse.healthSphere.service;

import com.csse.healthSphere.dto.BillRequest;
import com.csse.healthSphere.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;

    public BillService createBill(BillRequest billRequest){
        return null;
    }

    public List<BillService> getAllBills(){
        return List.of();
    }

    public BillService getBillById(Long id){
        return null;
    }

    public BillService updateBill(Long id, BillRequest billRequest){
        return null;
    }

    public void deleteBill(Long id){

    }
}
