package com.expleo.webcm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/webCM/employee")
public class EmployeeController {

    @GetMapping
    public String employee(){
        return "null";
    }
}
