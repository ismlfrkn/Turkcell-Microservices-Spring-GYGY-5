package com.turkcell.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/users")
@RestController
public class UserController {

    @GetMapping
    public String get() {
        System.out.println("User Service is working!");
        return "User Service is working!";

    }
}
