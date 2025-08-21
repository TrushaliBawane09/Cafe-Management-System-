package com.product.service.service;

import com.product.service.dto.CategoryDTO;
import com.product.service.dto.ProductDTO;
import com.product.service.dto.TotalProduct;
import com.product.service.entities.Product;
import com.product.service.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public ResponseEntity<ApiResponse> addProduct(Product product, MultipartFile image);

    public ResponseEntity<List<Product>> getProduct();

    public Product getProductById(int id);

    public ResponseEntity<ApiResponse> updateProduct(int id, ProductDTO updateProduct);

    public ResponseEntity<ApiResponse> deleteProduct(int id);

    public List<CategoryDTO> getCategories();

    public TotalProduct countProduct();


}
