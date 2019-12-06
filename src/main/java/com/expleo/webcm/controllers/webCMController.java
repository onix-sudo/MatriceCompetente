package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import com.expleo.webcm.helper.Principal;
import com.expleo.webcm.service.*;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Iterator;
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
    public String competencyMatrix(ModelMap model, @RequestParam(name = "proiectId") Integer proiectId) {

        List<ProiectSkill> skills = proiectService.showSkillsforProject(proiectId);
        UserExpleo userExpleo = userService.getUserExpleoPrincipal();

        model.addAttribute("skillList", proiectService.showSkillsforProject(proiectId));
        model.addAttribute("userSkillList", proiectService.showEvalForUserSkills(skills, userExpleo));

        return "cmptMat";
    }

    @GetMapping("/personalProfile")
    public String personalProfile(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();

        model.addAttribute("userSkills", userSkillService.getUserSkillByUser(user));
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

        System.out.println("text = " + text);
        UserExpleo user = userService.getUserExpleoPrincipal();
        List<Skill> searchResult = searchService.searchSkill(text);

        theModel.addAttribute("result", searchResult);
        theModel.addAttribute("user", user);

        return "skill-form";
    }

    @GetMapping("/personalProfile/showFormForAddSkill/search/addSkillToUser")
    public void addSkilltoUser(@RequestParam(value = "skillId") int skillId){
        userSkillService.saveUserSkill(userService.getUserExpleoPrincipal().getId(), skillId);

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
                          @RequestParam(value = "proiectId", required = false) int idproiect){

        UserExpleo user = userService.getUserExpleoPrincipal();
        userSkillService.saveUserSkill(user.getId(), idskill, eval);

        List<ProiectSkill> skills = proiectService.showSkillsforProject(idproiect);
        UserExpleo userExpleo = userService.getUserExpleoPrincipal();

        model.addAttribute("skillList", proiectService.showSkillsforProject(idproiect));
        model.addAttribute("userSkillList", proiectService.showEvalForUserSkills(skills, userExpleo));

        return "cmptMat";
    }

    @RequestMapping("/currentProj")
    public String currentProjects(ModelMap model) {
        List<Proiect> proiectList = proiectService.findPrincipalProjects();
        model.addAttribute("proiectList", proiectList);

        return "currentProj";
    }


}
