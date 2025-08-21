package com.auth_service.extservices;

import com.auth_service.dto.TotalBill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "COM.BILL.SERVICE")
public interface BillServiceExt {

    @GetMapping("/bills/total-bill")
    public TotalBill totalBill();
}
