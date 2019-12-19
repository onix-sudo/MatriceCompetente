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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * The Spring Controller for admin
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private SkillService skillService;

    /**
     * Get method that handles the request mapping below
     * @return the jsp file from view directory
     */
    @GetMapping()
    public String showAdmin(){
        return "admin_admin";
    }

    /**
     * Get method that handles the request mapping below
     * @return redirect to the jsp file from view directory
     */
    @GetMapping("/tomcatManager")
    public String tomcat(){
        return "redirect:/manager/st.jsp";
    }

    /**
     * Get method that handles the mapping of the request below to add a user form
     * @param theModel pass to the jsp file a UserExpleo object in order to provide the fields required to register
     *                 a new user .
     * @return the jsp file from view directory
     */
    @GetMapping("/addUser")
    public String addUser(Model theModel){

        UserExpleo employee = new UserExpleo();
        theModel.addAttribute("newEmployee", employee);

        return "admin_add-user";
    }

    /**
     * Post method that handles the mapping of the request below to save a new user
     * @param employee the object to be added into the database
     * @param result check if the fields are correct and if so, the object will be added in the database
     * @return the jsp file from view directory
     */
    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("newEmployee") UserExpleo employee, BindingResult result){

        if (result.hasErrors()){
            return "admin_add-user";
        }

            userService.saveNewUser(employee);
            userService.saveNewUserSecurityDb(employee);
            return "redirect:/admin";

    }

    /**
     * Get method that handles the mapping of the request below to update a user
     * @return redirect to the jsp file from view directory
     */
    @GetMapping("/updateUser")
    public String updateUser(){
        return "admin_search-update-user";
    }

    /**
     * Get method that handles the mapping of the request below to search a user
     * @param text the search term that will be used in order to retrieve the user you are looking for
     * @param theModel pass to the jsp the search result
     * @return the jsp file from view directory
     */
    @GetMapping("/updateUser/search")
    public String searchUsers(@RequestParam(value = "searchTerm") String text, Model theModel){

        List<UserExpleo> searchResult = searchService.searchUser(text.trim());
        theModel.addAttribute("result", searchResult);

        return "admin_search-update-user";
    }

    /**
     * Get method that handles the mapping of the request below to modify a user
     * @param theId the Id related to the user that will be modified
     * @param userModel pass values to render the view
     * @return the jsp file from view directory
     */
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

    /**
     * Post method that handles the mapping of the request below to remove a manager role
     * @param theId the Id related to the user that will not longer be a manager
     * @return redirect to the jsp file from view directory
     */
    @PostMapping("/updateUser/removeManagerRole")
    public String removeManagerRole(@RequestParam("userId") int theId){

        userService.removeManagerRole(theId);

        return "redirect:/admin/updateUser/modify?userId="+theId;
    }

    /**
     * Post method that handles the mapping of the request below to add a manager role
     * @param theId the Id related to the user that be a manager
     * @return redirect to the jsp file from view directory
     */
    @PostMapping("/updateUser/addManagerRole")
    public String addManagerRole(@RequestParam("userId") int theId){

        userService.addManagerRole(theId);

        return "redirect:/admin/updateUser/modify?userId="+theId;
    }

    /**
     * Post method that handles the mapping of the request below to update a user
     * @param updateUser the object that will be updated
     * @return redirect to the jsp file from view directory
     */
    @PostMapping("/updateUser/update")
    public String updateUserExpleo(@ModelAttribute ("user") UserExpleo updateUser){

        userService.updateUserExpleo(updateUser);

        return "redirect:/admin/updateUser";
    }

    /**
     * Get method that handles the mapping of the request below to add a skill
     * @param model pass to the jsp file a Skill object in order to provide the fields required to register
     *                 a new skill
     * @return the jsp file from view directory
     */
    @GetMapping("/addSkill")
    public String addSkill(Model model){
        Skill newSkill = new Skill();
        model.addAttribute("newSkill", newSkill);

        return "admin_addSkill";
    }

    /**
     * Post method that handles the mapping of the request below to save a new skill
     * @param skill the object to be added into the database
     * @return redirect to the jsp file from view directory
     */
    @PostMapping("/addSkill/save")
    public String saveNewSkill(@ModelAttribute("newSkill") Skill skill){

        skillService.saveSkill(skill);

        return "redirect:/admin";
    }


}
