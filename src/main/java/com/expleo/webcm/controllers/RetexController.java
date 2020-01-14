package com.expleo.webcm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/retex")
public class RetexController {

    @GetMapping
    public String indexPage(){
        return "retexIndex";
    }

    @GetMapping("/addNewRetex")
    public String addNewRetex(){
        return "retexAddNewRetex";
    }

    @PostMapping(value = "/saveNewRetex")
    public String saveNewRetex(){
        return "redirect:/retex";
    }
}
