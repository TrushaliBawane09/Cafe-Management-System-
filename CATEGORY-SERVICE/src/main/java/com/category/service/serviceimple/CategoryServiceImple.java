package com.category.service.serviceimple;

import com.category.service.dto.TotalCategory;
import com.category.service.entities.Category;
import com.category.service.payload.ApiResponse;
import com.category.service.repository.CategoryRepository;
import com.category.service.services.CategoryService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImple  implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> addCategory(Category category) {
        try{
            if(category == null){
                return new ResponseEntity<>( new ApiResponse("category is null", false, 400), HttpStatus.BAD_REQUEST);
            }
            category.setName(StringUtils.isBlank(category.getName()) ? "" : category.getName());
            this.categoryRepository.save(category);
            return new ResponseEntity<>(new ApiResponse("Category added successfully ", true, 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception getting category saving " +e);
            return new ResponseEntity<>(new ApiResponse("internal server error ", false,500 ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Page<Category>> getCategory(int page, int pageSize) {
        try{
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Category> categories = this.categoryRepository.findAll(pageable);
            if(categories.isEmpty()){
                return ResponseEntity.ok(Page.empty(pageable));
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception getting category fetching \" +e");
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Page.empty());
        }

    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> updateCategory(int id, Category updatedCategory) {
        try{
            Optional<Category> categoryOptional =  this.categoryRepository.findById(id);
            if(categoryOptional.isEmpty()){
                return new ResponseEntity<>(new ApiResponse("no categories found this ID ", false, 400), HttpStatus.BAD_REQUEST);
            }
            Category category = categoryOptional.get();
            category.setName(updatedCategory.getName());
            this.categoryRepository.save(category);
            return new ResponseEntity<>(new ApiResponse("Category updated successfully ", true, 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception getting category updating " +e);
            return new ResponseEntity<>(new ApiResponse("internal server error ", false,500 ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> deleteCategory(int id) {
        try{
            Optional<Category> categoryOptional =  this.categoryRepository.findById(id);
            if(categoryOptional.isEmpty()){
                return new ResponseEntity<>(new ApiResponse("no categories found this ID ", false, 400), HttpStatus.BAD_REQUEST);
            }
            Category category= categoryOptional.get();
            this.categoryRepository.delete(category);
            return new ResponseEntity<>(new ApiResponse("Category deleted successsfully ", true, 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception getting category deleting " +e);
            return new ResponseEntity<>(new ApiResponse("internal server error ", false,500 ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Category getCategoryById(int id) {
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        Category category = categoryOptional.get();
        return category;
    }

    @Override
    public TotalCategory countCategory() {
        List<Category> categories = this.categoryRepository.findAll();
        TotalCategory t = new TotalCategory();
        t.setTotalCategory(categories.size());
       return t;
    }

    @Override
    public List<Category> searchCategory(String name) {
        try{
            return this.categoryRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            log.error("error while searching for category ");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }
}
