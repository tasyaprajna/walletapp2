package com.example.introduction.ui.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employee")
public class EmployeeControllerr {
    @GetMapping
    public String getEmployee(){
        return "Balikan dari GetMapping.";
    }

    @PostMapping
    public String postEmployee(){
        return "Balikan dari PostMapping.";
    }

    @DeleteMapping
    public String deleteEmployee(){
        return "Balikan dari DeleteMapping.";
    }

    @PatchMapping
    public String patchEmployee(){
        return "Balikan dari PatchMapping.";
    }

    @PutMapping
    public String putEmployee(){
        return "Balikan dari PutMapping.";
    }
}
