package com.bill.service.service;

import com.bill.service.dto.CategoryDTO;
import com.bill.service.dto.ProductDTO;
import com.bill.service.dto.TotalBill;
import com.bill.service.entities.Bill;
import com.bill.service.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {

    public ResponseEntity<ApiResponse> generateBill(Bill bill);

    public List<CategoryDTO> getAllCategories();

    public List<ProductDTO> getProducts();

    public TotalBill totalBills();

    public ResponseEntity<List<Bill>> getBills();

    public ResponseEntity<ApiResponse> deleteBill(int id);

    public ResponseEntity<byte[]> getPdf(Map<String , Object> mapRequest);
}
