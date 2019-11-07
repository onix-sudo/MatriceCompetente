package com.expleo.webcm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("retex/leaders")
public class LeadersController {

    @GetMapping
    public String showLeaders(){
        return "leaders";
    }
}
