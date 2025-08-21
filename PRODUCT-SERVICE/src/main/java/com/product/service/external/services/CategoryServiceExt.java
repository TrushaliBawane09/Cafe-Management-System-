package com.product.service.external.services;

import com.product.service.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "COM.CATEGORY.SERVICE")
public interface CategoryServiceExt {

    @GetMapping("/category/{id}")
    CategoryDTO  getCategoryById(@PathVariable int id);

    @GetMapping("/category/getAllCategories")
    public List<CategoryDTO> getAllCategories();
}
