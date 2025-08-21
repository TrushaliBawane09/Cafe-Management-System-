package com.product.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private int id;
    private String name ;
    private String description;
    private int price;
    private String image;
    private byte[] imageBytes;
    private int categoryId;
    private  CategoryDTO categoryDTO;
    private String imageUrl;

}
