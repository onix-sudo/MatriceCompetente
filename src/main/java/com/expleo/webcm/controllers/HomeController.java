package com.expleo.webcm.controllers;

import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String showHome(ModelMap model){
        model.addAttribute("nume", userService.getUserExpleoPrincipal().getPrenume());
        return "home";
    }

}
