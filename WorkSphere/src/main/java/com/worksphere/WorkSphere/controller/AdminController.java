package com.worksphere.WorkSphere.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/hello")
    public String helloAdmin() {
        return "Hello ADMIN";
    }
}