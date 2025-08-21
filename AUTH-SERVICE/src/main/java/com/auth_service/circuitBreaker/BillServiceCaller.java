package com.auth_service.circuitBreaker;

import com.auth_service.dto.TotalBill;
import com.auth_service.extservices.BillServiceExt;
import com.netflix.discovery.converters.Auto;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillServiceCaller {

    @Autowired
    private BillServiceExt billServiceExt;

    @CircuitBreaker(name = "billService", fallbackMethod = "billFallback")
    public TotalBill getTotalBill(){
        try{
            TotalBill totalBill = this.billServiceExt.totalBill();
            return totalBill;
        } catch (FeignException e) {
            log.error("Feign error while calling Bill Service: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error in getBillCount: {}", e.getMessage(), e);
            throw new RuntimeException("Bill Service call failed", e);
        }
    }

    public TotalBill billFallback(Throwable t) {
        System.out.println("heeeeeeee falbacckkk");
        log.error("Bill service fallback triggered: {}", t.getMessage());
        return new TotalBill(0);
    }
}
