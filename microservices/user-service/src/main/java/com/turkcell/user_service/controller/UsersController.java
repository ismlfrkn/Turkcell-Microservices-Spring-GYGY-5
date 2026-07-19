package com.turkcell.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/users")
@RestController
public class UsersController {

    @GetMapping()
    public String hello()
    {
        return "Hello";
    }
}
