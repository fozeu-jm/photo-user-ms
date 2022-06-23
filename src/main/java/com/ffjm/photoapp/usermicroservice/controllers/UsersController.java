package com.ffjm.photoapp.usermicroservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @RequestMapping("/status/check")
    public String status(){
        return "Working !!";
    }
}
