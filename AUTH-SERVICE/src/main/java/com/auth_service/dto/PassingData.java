package com.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PassingData {
    private String token ;
    private String name;
    private String role;
    private boolean status;
}
