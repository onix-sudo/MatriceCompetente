package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.service.*;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/webCM")
public class webCMController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProiectService proiectService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private SearchService searchService;

    @GetMapping
    public String webCM(ModelMap model){
        return "webCM";
    }

    @GetMapping(value = "/cmptMat")
    public String competencyMatrix(ModelMap model, @RequestParam(name = "proiectId") Integer projectId) {

//        List<ProiectSkill> skills = proiectService.showSkillsforProject(proiectId);
//        UserExpleo userExpleo = userService.getUserExpleoPrincipal();

//        model.addAttribute("skillList", skills);
//        model.addAttribute("userSkillList", proiectService.showEvalForUserSkills(skills, userExpleo));
        List<UserSkill> userSkillsFromProject = userSkillService.getUserSkillByProjectSkills(projectId);
        model.addAttribute("userSkillList", userSkillsFromProject);
        model.addAttribute("projectId", projectId);

        return "cmptMat";
    }

    @GetMapping("/personalProfile")
    public String personalProfile(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();
        List<UserSkill> userAdditionalSkills = new LinkedList<>();
        List<UserSkill> projectSkills = new LinkedList<>();

        userSkillService.getAdditionalAndProjectSkill(user.getId(), userAdditionalSkills, projectSkills);

        model.addAttribute("projectSkills", projectSkills);
        model.addAttribute("userSkills", userAdditionalSkills);
        model.addAttribute("user", user);

        return "personalProfile";
    }

    @RequestMapping("/personalProfile/showFormForAddSkill")
    public String showFormForAddSkill(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();
        Skill theSkill = new Skill();

        model.addAttribute("skill", theSkill);
        model.addAttribute("user", user);

        return "skill-form";
    }

    @GetMapping("/personalProfile/showFormForAddSkill/search")
    public String searchSkills(@RequestParam(value = "searchTerm") String text, Model theModel){

        UserExpleo user = userService.getUserExpleoPrincipal();
        List<Skill> searchResult = searchService.searchPrincipalSkill(text, user.getId());

        theModel.addAttribute("result", searchResult);
        theModel.addAttribute("user", user);

        return "skill-form";
    }

    @GetMapping("/personalProfile/showFormForAddSkill/search/addSkillToUser")
    public void addSkilltoUser(@RequestParam(value = "skillId") int skillId,
                               @RequestParam(value = "userID") int userId){
        userSkillService.saveUserSkill(userId, skillId);

    }

    @GetMapping("/deleteSkill")
    public void deleteSkill(@RequestParam("skillId") int idSkill){

        UserExpleo user = userService.getUserExpleoPrincipal();
        userSkillService.removeUserSkill(user.getId(), idSkill);

    }

    @RequestMapping("/modifyP")
    public void modify(@RequestParam("evaluation") int eval, @RequestParam("idskill") int theId){
        UserExpleo user = userService.getUserExpleoPrincipal();
        userSkillService.saveUserSkill(user.getId(), theId, eval);

    }

    @GetMapping(value = "/cmptMat/modifyT")
    public String modifyT(ModelMap model, @RequestParam("evaluation") int eval, @RequestParam("idskill") int idskill,
                          @RequestParam(value = "proiectId") int idproiect){

        UserExpleo user = userService.getUserExpleoPrincipal();
        userSkillService.saveUserSkill(user.getId(), idskill, eval);

        return competencyMatrix(model, idproiect);
    }

    @RequestMapping("/currentProj")
    public String currentProjects(ModelMap model) {
        List<Proiect> proiectList = proiectService.findPrincipalProjects();
        model.addAttribute("proiectList", proiectList);

        return "currentProj";
    }


}
