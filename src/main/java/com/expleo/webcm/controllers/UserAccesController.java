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
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.List;

/**
 * The Spring Controller for user acces
 */


@Controller
public class UserAccesController {

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    /**
     * Get method that handles the request mapping below
     * @return the login jsp file from view directory
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Get method that handles the request mapping below
     * @return the accessDenied jsp file from view directory
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }

    /**
     * Get method that handles the request mapping below
     * @param model pass to the jsp file the password object
     * @return the user_changePassword jsp file from view directory
     */
    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        Password password = new Password();
        model.addAttribute("password", password);
        return "user_changePassword";
    }

    /**
     * Post method that handles the request mapping below
     * @param model pass to the jsp file the error messages
     * @param password take the new password, validates it and then changes it
     * @return the successChangePassoword jsp file from view directory
     */
    @PostMapping("/changePassword/save")
    public String saveChangePassword(@Valid @ModelAttribute("password") Password password, ModelMap model) {
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

    /**
     * Get method that handles the request mapping below
     * @return the forgotPassword jsp file from view directory
     */
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    /**
     * Get method that handles the request mapping below
     * @param email the email typed by the user
     * @param model pass the email to the jsp file
     * @return the forgotPassword jsp file from view directory
     */
    @GetMapping("/forgotPassword/email")
    public String forgotPasswordEmailNotExist(@RequestParam("notExist") String email, ModelMap model) {
        if(!email.isEmpty()){
            model.addAttribute("email", email);
        }
        return "forgotPassword";
    }

    /**
     * Post method that handles the request mapping below in order to reset the password
     * @param email the email typed by the user
     * @return redirect to the jsp file from view directory
     */
    @PostMapping("forgotPassword/reset")
    public String resetPassword(@RequestParam("email") String email) {

        try {
            UserExpleo user = userService.getUserExpleoByEmail(email.trim());

            if (user != null) {
                String token = userService.createResetPasswordDetails(user.getId());
                mailService.sendMail(token, user.getEmail(), user.getPrenume());
                return "redirect:/forgotPassword/success?email=" + email.trim();
            }
        } catch (NoResultException e) {
            System.out.println(e);
        }
        return "redirect:/forgotPassword/email?notExist=" + email.trim();
    }

    /**
     * Get method that handles the request mapping below in order to reset the password with success
     * @param email the current email
     * @param model pass the email to the jsp file
     * @return the jsp file from view directory
     */
    @GetMapping("/forgotPassword/success")
    public String successResetToken(@RequestParam("email") String email, ModelMap model) {

        model.addAttribute("email", email);

        return "successResetPasswordTokenCreate";
    }

    /**
     * Get method that handles the request mapping below in order to set the new password
     * @param token the token on the basis of which the user can change the password(available for 30 minutes)
     * @param model pass the fields below to the jsp
     * @return the jsp file from view directory
     */
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

    /**
     * Post method that handles the request mapping below in order to save the new password
     * @param password take the new password, validates it and then changes it
     * @param token the token on the basis of which the user can change the password(available for 30 minutes)
     * @param id the id related to the user who wants to reset his password
     * @param model pass the fields below to the jsp
     * @return the jsp file from view directory
     */
    @PostMapping("/forgotPassword/newPassword")
    public String saveNewPassword(@ModelAttribute("password") Password password,
                                  @RequestParam(value = "userId") Integer id,
                                  @RequestParam("token") String token,
                                  ModelMap model) {

        List<String> errorMessages = new PasswordValidator().isValid(password.getNewPassword());
        model.addAttribute("token", token);

        if(password.getNewPassword().isEmpty() || password.getConfirmPassword().isEmpty()) {
            errorMessages.add("Nu ati introdus nimic.");
            model.addAttribute("errors", errorMessages);
            return newPassword(token, model);
        } else if(!password.getNewPassword().equals(password.getConfirmPassword())){
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
