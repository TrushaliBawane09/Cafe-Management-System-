package com.bill.service.extservice;

import com.bill.service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "COM.PRODUCT.SERVICE")
public interface ProductServiceExt {

    @GetMapping("/product")
    public List<ProductDTO> getProducts();
}
