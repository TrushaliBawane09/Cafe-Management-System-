package com.auth_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test1")
    public String myLove(){
        System.out.println("this is myLove http ");
        return "jayy ram ji ki";
    }


}
