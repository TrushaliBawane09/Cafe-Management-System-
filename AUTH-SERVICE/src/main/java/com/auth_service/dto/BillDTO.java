package com.auth_service.dto;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDTO {

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
