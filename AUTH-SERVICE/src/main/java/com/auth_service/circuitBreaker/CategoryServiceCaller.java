package com.auth_service.circuitBreaker;

import com.auth_service.dto.TotalBill;
import com.auth_service.dto.TotalCategory;
import com.auth_service.extservices.CategoryExtService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceCaller {

    @Autowired
    private CategoryExtService categoryExtService;

    @CircuitBreaker(name = "categoryService", fallbackMethod = "categoryFallback")
    public TotalCategory getTotalcategory(){
        try{
            TotalCategory totalCategory = this.categoryExtService.countCategory();
            return totalCategory;
        }catch (FeignException e){
            log.error("Feign error while calling Category Service: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error in getCategoryCount: {}", e.getMessage(), e);
            throw new RuntimeException("Category Service call failed", e);
        }
    }

    public TotalCategory categoryFallback(Throwable t) {
        log.error("Category service fallback triggered: {}", t.getMessage());
        return new TotalCategory(0);
    }

}
