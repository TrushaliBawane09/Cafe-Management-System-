package com.product.service.serviceimple;

import com.product.service.dto.CategoryDTO;
import com.product.service.dto.ProductDTO;
import com.product.service.dto.TotalProduct;
import com.product.service.entities.Product;
import com.product.service.external.services.CategoryServiceExt;
import com.product.service.payload.ApiResponse;
import com.product.service.repository.ProductRepository;
import com.product.service.service.ProductService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImple implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryServiceExt categoryServiceExt;

    public static final String uploadImg = "E:\\Extracted_java_projects\\pdfFolder\\product-image";
    @Transactional
    @Override
    public ResponseEntity<ApiResponse> addProduct(Product product, MultipartFile image) {
        try{
            product.setName(StringUtils.isBlank(product.getName()) ? "" : product.getName());
            product.setDescription(StringUtils.isBlank(product.getDescription()) ? "" : product.getDescription());
            product.setPrice(product.getPrice());
            product.setCategoryId(product.getCategoryId());
            File folder = new File(uploadImg);
            if(!folder.exists()){
                folder.mkdir();
            }
            File destFile = new File(folder,image.getOriginalFilename());
            image.transferTo(destFile);
            product.setImage(destFile.getName());
            this.productRepository.save(product);
            return new ResponseEntity<>(new ApiResponse("Product saved successfully ", true, 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error("exception while saving product ");
            return new ResponseEntity<>(new ApiResponse("exception while saving product ", false, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<Product>> getProduct() {
        List<Product> proList ;
        try{
          List<Product> productList = this.productRepository.findAll();
          if(productList.isEmpty()){
              return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
          }
          for (Product product :  productList){
                String imagePath = uploadImg +File.separator+ product.getImage() ;
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
                product.setImageBytes(imageBytes);
                CategoryDTO productFromCategory= this.categoryServiceExt.getCategoryById(product.getCategoryId());
                product.setCategoryDTO(productFromCategory);
          }
          return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error while fetching product");
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateProduct(int id, ProductDTO updateProduct) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if(productOptional.isEmpty()){
           return new ResponseEntity<>(new ApiResponse("not found this product", false, 400), HttpStatus.BAD_REQUEST);
        }
        Product product = productOptional.get();
        product.setName(updateProduct.getName());
        product.setPrice(updateProduct.getPrice());
        product.setDescription(updateProduct.getDescription());
        this.productRepository.save(product);
        return new ResponseEntity<>(new ApiResponse("Product updated successfully ", true, 200), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> deleteProduct(int id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if(productOptional.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("Product not exist ", false , 400),HttpStatus.BAD_REQUEST);
        }
        Product product = productOptional.get();
        this.productRepository.delete(product);
        return new ResponseEntity<>(new ApiResponse("Product Deleted Successfully ", true, 200), HttpStatus.OK);
    }


    @Override
    public Product getProductById(int id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
         Product product = productOptional.get();
        CategoryDTO categoryDTO =  this.categoryServiceExt.getCategoryById(product.getCategoryId());
        product.setCategoryDTO(categoryDTO);
        return product;
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<CategoryDTO> categories = this.categoryServiceExt.getAllCategories();
        return categories;
    }

    @Override
    public TotalProduct countProduct() {
        List<Product> productList =this.productRepository.findAll();
        TotalProduct t = new TotalProduct();
        t.setTotalProduct(productList.size());
        return t;
    }

}
