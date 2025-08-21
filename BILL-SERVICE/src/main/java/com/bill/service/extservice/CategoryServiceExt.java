package com.bill.service.extservice;

import com.bill.service.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "COM.CATEGORY.SERVICE")
public interface CategoryServiceExt {

    @GetMapping("/category/getAllCategories")
    public List<CategoryDTO> getAllCategories();
}
