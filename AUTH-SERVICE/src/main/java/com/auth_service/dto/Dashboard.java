package com.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {

    private Integer  totalCategory;
    private  Integer totalproduct;
    private Integer totalBill;
}
