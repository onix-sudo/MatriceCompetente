package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.securitydb.LoginRoles;
import com.expleo.webcm.entity.securitydb.LoginUser;
import com.expleo.webcm.service.SearchService;
import com.expleo.webcm.service.SkillService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    SearchService searchService;

    @Autowired
    SkillService skillService;

    //add req mapping for /admin
    @GetMapping()
    public String showAdmin(){
        return "admin_admin";
    }

    @RequestMapping("/tomcatManager")
    public String tomcat(){
        return "redirect:/manager/st.jsp";
    }

    //add a new user
    @GetMapping("/addUser")
    public String addUser(Model theModel){

        UserExpleo employee = new UserExpleo();
        theModel.addAttribute("newEmployee", employee);

        return "admin_add-user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("newEmployee") UserExpleo employee, BindingResult result){

        if (result.hasErrors()){
            return "admin_add-user";
        }

        userService.saveNewUser(employee);
        userService.saveNewUserSecurityDb(employee);

        return "redirect:/admin";
    }

    @GetMapping("/updateUser")
    public String updateUser(){
        return "admin_search-update-user";
    }

    @GetMapping("/updateUser/search")
    public String searchUsers(@RequestParam(value = "searchTerm") String text, Model theModel){

        List<UserExpleo> searchResult = searchService.searchUser(text);
        theModel.addAttribute("result", searchResult);

        return "admin_search-update-user";
    }

    @GetMapping("/updateUser/modify")
    public ModelAndView modifyUser(@RequestParam("userId") int theId, ModelMap userModel){
        boolean managerCheck = false;
        ModelAndView modelAndView = new ModelAndView();

        UserExpleo userExpleo = userService.getUserExpleoById(theId);
        LoginUser loginUser = userService.getLoginUserById(theId);

        for(LoginRoles loginRoles:loginUser.getRole()){
            if(loginRoles.getRoles().contains("ROLE_MANAGER")){
                managerCheck = true;
                break;
            }
        }

        userModel.addAttribute("managerCheck", managerCheck);
        userModel.addAttribute("user", userExpleo);
        modelAndView.addAllObjects(userModel);

        modelAndView.setViewName("admin_update-user");

        return modelAndView;
    }

    @PostMapping("/updateUser/removeManagerRole")
    public String removeManagerRole(@RequestParam("userId") int theId){

        userService.removeManagerRole(theId);

        return "redirect:/admin/updateUser/modify?userId="+theId;
    }

    @PostMapping("/updateUser/addManagerRole")
    public String addManagerRole(@RequestParam("userId") int theId){

        userService.addManagerRole(theId);

        return "redirect:/admin/updateUser/modify?userId="+theId;
    }

    @PostMapping("/updateUser/update")
    public String updateUserExpleo(@ModelAttribute ("user") UserExpleo updateUser){

        userService.updateUserExpleo(updateUser);

        return "redirect:/admin/updateUser";
    }

    @GetMapping("/addSkill")
    public String addSkill(Model model){
        Skill newSkill = new Skill();
        model.addAttribute("newSkill", newSkill);

        return "admin_addSkill";
    }

    @PostMapping("/addSkill/save")
    public String saveNewSkill(@ModelAttribute("newSkill") Skill skill){

        skillService.saveSkill(skill);

        return "redirect:/admin";
    }


}
