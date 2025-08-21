package com.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor

public class UserWrapper {

    private int id;
    private String name;
    private String email;
    private String contact;
    private String role;
    private boolean status;

    public UserWrapper(String name, String contact, String email, boolean status) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.status = status;
    }
}
