package com.auth_service.extservices;

import com.auth_service.dto.TotalCategory;
import com.auth_service.dto.TotalProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="COM.PRODUCT.SERVICE")
public interface ProductExtService {

    @GetMapping("/product/count-product")
    public TotalProduct countProduct();
}
