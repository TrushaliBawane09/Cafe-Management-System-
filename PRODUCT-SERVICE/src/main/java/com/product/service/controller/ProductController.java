package com.product.service.controller;

import com.product.service.dto.CategoryDTO;
import com.product.service.dto.ProductDTO;
import com.product.service.dto.TotalProduct;
import com.product.service.entities.Product;
import com.product.service.payload.ApiResponse;
import com.product.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> addProdict(@RequestPart Product product, @RequestPart MultipartFile image) {
        if (product == null) {
           return new ResponseEntity<>(new ApiResponse("products are null" , false, 400), HttpStatus.BAD_REQUEST);
        }
      return  this.productService.addProduct(product, image);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getALlProduct(){
        return this.productService.getProduct();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id){
      return  this.productService.getProductById(id);
    }

    @GetMapping("/getCategories")
    public List<CategoryDTO> getCategories(){
       return  this.productService.getCategories();
    }

    @GetMapping("/count-product")
    public TotalProduct countProduct(){
        return this.productService.countProduct();
    }

    @PostMapping("update-product/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable int id, @RequestBody ProductDTO product){
        return this.productService.updateProduct(id, product);
    }

    @DeleteMapping("delete-product/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int id){
        return this.productService.deleteProduct(id);
    }
}
