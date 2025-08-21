package com.auth_service.circuitBreaker;

import com.auth_service.dto.TotalProduct;
import com.auth_service.extservices.ProductExtService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceCaller {

    @Autowired
    private ProductExtService productExtService;

    @CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public TotalProduct getTotalproduct(){
        try{
            TotalProduct totalProduct =  this.productExtService.countProduct();
            return totalProduct;
        } catch (FeignException e) {
            log.error("Feign error while calling product Service: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error in getProductCount: {}", e.getMessage(), e);
            throw new RuntimeException("Product Service call failed", e);
        }
    }

    public TotalProduct productFallback(Throwable t) {
        log.error("Product service fallback triggered: {}", t.getMessage());
        return new TotalProduct(0);
    }
}
