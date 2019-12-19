package com.expleo.webcm.controllers;

import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The Spring Controller for home .
 */


@Controller
public class HomeController {
    @Autowired
    UserService userService;

    /**
     * Get method that handles the request mapping below.
     * @param model pass to the jsp file the principal user object .
     * @return the jsp file from view directory
     */
    @GetMapping("/")
    public String showHome(ModelMap model){
        model.addAttribute("nume", userService.getUserExpleoPrincipal().getPrenume());
        return "home";
    }

}
