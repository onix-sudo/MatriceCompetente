package com.expleo.webcm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/retex")
public class RetexController {

    @GetMapping
    public String retex(){
        return "retex";
    }
}
