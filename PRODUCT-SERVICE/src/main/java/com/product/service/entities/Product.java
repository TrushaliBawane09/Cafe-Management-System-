package com.product.service.entities;

import com.product.service.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private int price;
    private int categoryId;
    private String image;

    @Transient
    private byte[] imageBytes;

    @Transient
   private CategoryDTO categoryDTO;
}
