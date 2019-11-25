package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginUser;
import com.expleo.webcm.helper.Password;
import com.expleo.webcm.helper.PasswordValidator;
import com.expleo.webcm.service.MailService;
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
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserAccesController {

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

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
    public String saveChangePassword(@ModelAttribute("password") Password password, ModelMap model) {
        List<String> errorMessages = new PasswordValidator().isValid(password.getNewPassword());


        if(!userService.checkIfValidOldPassowrd(password.getOldPassword())){

            errorMessages.clear();
            errorMessages.add("Parola actuala este introdusa gresit.");
            model.addAttribute("errors", errorMessages);

            return "user_changePassword";
        } else if(!password.getNewPassword().equals(password.getConfirmPassword())){
            errorMessages.add("Confirmarea parolei nu coincide cu noua parola.");
            model.addAttribute("errors", errorMessages);
            return "user_changePassword";

        } else if(errorMessages != null){
            model.addAttribute("errors", errorMessages);
            return "user_changePassword";
        } else {
            userService.changePassword(password.getNewPassword(), userService.getUserExpleoPrincipal().getId());
            return "successChangePassoword";
        }
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
                String token = userService.createResetPasswordDetails(user.getId());
                mailService.sendMail(token, user.getEmail());
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
            model.addAttribute("token", token);
        }
        model.addAttribute("loginUser", loginUser);

        return "user_resetPassword";
    }

    @PostMapping("/forgotPassword/newPassword")
    public String saveNewPassword(@ModelAttribute("password") Password password,
                                  @RequestParam(value = "userId") Integer id,
                                  @RequestParam("token") String token,
                                  ModelMap model) {

        List<String> errorMessages = new PasswordValidator().isValid(password.getNewPassword());
        model.addAttribute("token", token);

         if(!password.getNewPassword().equals(password.getConfirmPassword())){
            errorMessages.add("Confirmarea parolei nu coincide cu noua parola.");
            model.addAttribute("errors", errorMessages);
            return newPassword(token, model);

        } else if(errorMessages != null){
            model.addAttribute("errors", errorMessages);
            return newPassword(token, model);
        } else {
            userService.changePassword(password.getNewPassword(), id);
            return "successChangePassoword";
        }
    }
}
