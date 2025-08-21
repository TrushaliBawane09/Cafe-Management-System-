package com.auth_service.extservices;

import com.auth_service.dto.Dashboard;
import com.auth_service.dto.TotalCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@FeignClient(name ="COM.CATEGORY.SERVICE")
public interface CategoryExtService {

    @GetMapping("/category/count-category")
    public TotalCategory countCategory();
}
