package com.bill.service.entities;

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
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String uuid;
    private String name;
    private String email;
    private String contactNumber;
    private String paymentMode;
    private Integer total;

    @Lob //store the JSON array
    private String productDetails;
    private String createdBy;
}
