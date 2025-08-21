package com.bill.service.controller;

import com.bill.service.dto.CategoryDTO;
import com.bill.service.dto.ProductDTO;
import com.bill.service.dto.TotalBill;
import com.bill.service.entities.Bill;
import com.bill.service.payload.ApiResponse;
import com.bill.service.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/generate-bill")
    public ResponseEntity<ApiResponse> generateBill(@RequestBody Bill bill){
        return this.billService.generateBill(bill);
    }

    @GetMapping("/getCategories")
    public List<CategoryDTO> getCategories(){
        return this.billService.getAllCategories();
    }

    @GetMapping("/product")
    public List<ProductDTO> getProducts(){
        return this.billService.getProducts();
    }

    @GetMapping("/total-bill")
    public TotalBill totalBill(){
        return this.billService.totalBills();
    }

    @GetMapping("/getBills")
    public ResponseEntity<List<Bill>> getBills(){
        return this.billService.getBills();
    }

    @DeleteMapping("/delete-bill/{id}")
    public ResponseEntity<ApiResponse> deleteBill(@PathVariable int id){
       return this.billService.deleteBill(id);
    }
}
