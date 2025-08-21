package com.category.service.services;

import com.category.service.dto.TotalCategory;
import com.category.service.entities.Category;
import com.category.service.payload.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    public ResponseEntity<ApiResponse> addCategory(Category category);

    public ResponseEntity<Page<Category>> getCategory(int page, int pageSize);

    public  Category getCategoryById(int id);

    public ResponseEntity<ApiResponse> updateCategory(int id, Category category);

    public ResponseEntity<ApiResponse> deleteCategory(int id);

    public TotalCategory countCategory();

    public List<Category> searchCategory(String name );

    public List<Category> getAllCategories();
}
