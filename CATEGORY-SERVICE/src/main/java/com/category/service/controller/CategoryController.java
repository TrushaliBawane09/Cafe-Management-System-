package com.category.service.controller;

import com.category.service.dto.TotalCategory;
import com.category.service.entities.Category;
import com.category.service.payload.ApiResponse;
import com.category.service.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse>  addCategory( @RequestBody Category category){
        if(category  == null){
            return new ResponseEntity<>(new ApiResponse("category is null", false, 400), HttpStatus.BAD_REQUEST);
        }
       return this.categoryService.addCategory(category);
    }

    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(@RequestParam(defaultValue =  "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        return this.categoryService.getCategory(page, pageSize);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable int id, @RequestBody Category updatedCategory ){
       return this.categoryService.updateCategory(id,updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id){
      return  this.categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id){
       return  this.categoryService.getCategoryById(id);
    }

    @GetMapping("/count-category")
    public TotalCategory countCategory(){
       return this.categoryService.countCategory();
    }

    @GetMapping("/search-category/{categoryName}")
    public List<Category> searchCategory(@PathVariable String categoryName){
        return this.categoryService.searchCategory(categoryName);
    }

    @GetMapping("getAllCategories")
    public List<Category> getAllCategories(){
       return this.categoryService.getAllCategories();
    }
}

