package com.expleo.webcm.controllers;

import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserAccesController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login (){
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "accessDenied";
    }

    @GetMapping("/changePassword")
    public String changePassword(){
        return "user_changePassword";
    }

    @PostMapping("/changePassword/save")
    public String saveNewPassword(@RequestParam(value = "oldPassword") String oldPassword,
                                  @RequestParam(value = "password") String newPassword,
                                  @RequestParam(value = "confirmPassword") String confirmPassword){

        if(userService.checkIfValidOldPassowrd(oldPassword)){
            if(newPassword.equals(confirmPassword)){
                userService.changePassword(newPassword);
            }
        }
        return "redirect:/webCM/personalProfile";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(){
        return "forgotPassword";
    }

    @GetMapping("forgotPassword/newPassword")
    public String resetPassword(){
        return "resetPassword";
    }

}
