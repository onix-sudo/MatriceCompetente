package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginUser;
import com.expleo.webcm.helper.Password;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.NoResultException;

@Controller
public class UserAccesController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        Password password = new Password();
        model.addAttribute("password", password);
        return "user_changePassword";
    }

    @PostMapping("/changePassword/save")
//    public String saveChangePassword(@RequestParam(value = "oldPassword") String oldPassword,
//                                     @RequestParam(value = "password") String newPassword,
//                                     @RequestParam(value = "confirmPassword") String confirmPassword) {

    public String saveChangePassword(@ModelAttribute("password") Password password) {

        if (userService.checkIfValidOldPassowrd(password.getOldPassword())) {
            if (password.getNewPassword().equals(password.getConfirmPassword())) {
                userService.changePassword(password.getNewPassword(), userService.getUserExpleoPrincipal().getId());
            }
        }
        return "successChangePassoword";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @GetMapping("/forgotPassword/email")
    public String forgotPasswordEmailNotExist(@RequestParam("notExist") String email, ModelMap model) {
        if(!email.isEmpty()){
            model.addAttribute("email", email);
        }
        return "forgotPassword";
    }

    @PostMapping("forgotPassword/reset")
    public String resetPassword(@RequestParam("email") String email) {

        try {
            UserExpleo user = userService.getUserExpleoByEmail(email.trim());

            if (user != null) {
                userService.createResetPasswordDetails(user.getId());
                return "redirect:/forgotPassword/success?email=" + email.trim();
            }
        } catch (NoResultException e) {
            System.out.println(e);
        }
        return "redirect:/forgotPassword/email?notExist=" + email.trim();
    }

    @GetMapping("/forgotPassword/success")
    public String successResetToken(@RequestParam("email") String email, ModelMap model) {

        model.addAttribute("email", email);

        return "successResetPasswordTokenCreate";
    }

    @GetMapping("/forgotPassword/newPassword")
    public String newPassword(@RequestParam("token") String token, ModelMap model)
    {
        LoginUser loginUser = userService.getLoginUserByToken(token);

        if(loginUser != null) {
            Password password = new Password();
            UserExpleo userExpleo = userService.getUserExpleoById(loginUser.getId());

            model.addAttribute("userExpleo", userExpleo);
            model.addAttribute("password", password);
        }
        model.addAttribute("loginUser", loginUser);

        return "user_resetPassword";
    }

    @PostMapping("/forgotPassword/newPassword/save")
    public String saveNewPassword(@ModelAttribute("password") Password password,
                                  @RequestParam(value = "userId") Integer id) {

            if (password.getNewPassword().equals(password.getConfirmPassword())) {
                userService.changePassword(password.getNewPassword(), id);
            }

        return "successChangePassoword";
    }
}
