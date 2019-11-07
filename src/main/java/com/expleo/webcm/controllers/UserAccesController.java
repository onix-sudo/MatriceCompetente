package com.expleo.webcm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAccesController {

    @GetMapping("/login")
    public String login (){
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "accessDenied";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(){
        return "resetPassword";
    }

}
