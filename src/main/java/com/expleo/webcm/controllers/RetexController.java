package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import com.expleo.webcm.service.*;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/retex")
public class RetexController {
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
    public String retex(ModelMap model){
        List<Proiect> proiectList = proiectService.findProjectByUser(userService.getUserExpleoPrincipal());
        model.addAttribute("proiectList", proiectList);

        return "retex";
    }

    @GetMapping(value = "/cmptMat")
    public String competencyMatrix(ModelMap model, @RequestParam(name = "proiectId") Integer proiectId) {

        List<ProiectSkill> skills = proiectService.showSkillsforProject(proiectId);
        model.addAttribute("skillList", proiectService.showSkillsforProject(proiectId));

        System.out.println("proiectId = " + proiectId);
        UserExpleo userExpleo = userService.getUserExpleoPrincipal();
        model.addAttribute("userSkillList", proiectService.showEvalForUserSkills(skills, userExpleo));

        return "cmptMat";
    }

    @GetMapping("/personalProfile")
    public String personalProfile(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();

        List<UserSkill> userSkills = userSkillService.getUserSkillByUser(user);

        model.addAttribute("userSkills", userSkills);

        model.addAttribute("user", user);

        return "personalProfile";
    }

    @GetMapping("/personalProfile/showFormForAddSkill")
    public String showFormForAddSkill(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();

        Skill theSkill = new Skill();

        model.addAttribute("skill", theSkill);

        model.addAttribute("user", user);

        return "skill-form";
    }

    @GetMapping("/personalProfile/showFormForAddSkill/search")
    public String searchSkills(@RequestParam(value = "searchTerm") String text, Model theModel){

        List<Skill> searchResult = searchService.searchSkill(text);
        theModel.addAttribute("result", searchResult);

        return "skill-form";
    }

    @GetMapping("/personalProfile/showFormForAddSkill/search/addSkillToUser")
    public String addSkilltoUser(@RequestParam(value = "skillId") int skillId){

        UserExpleo user = userService.getUserExpleoPrincipal();

        userSkillService.saveUserSkill(user.getId(), skillId);

        return "redirect:/retex/personalProfile";
    }

    @GetMapping("/deleteSkill")
    public String deleteSkill(@RequestParam("skillId") int idSkill){

        UserExpleo user = userService.getUserExpleoPrincipal();


        userSkillService.removeUserSkill(user.getId(), idSkill);

        return "redirect:/retex/personalProfile";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("evaluation") int eval, @RequestParam("idskill") int theId){

        UserExpleo user = userService.getUserExpleoPrincipal();

        userSkillService.saveUserSkill(user.getId(), theId, eval);

        return "redirect:/retex/personalProfile";
    }

    @GetMapping("/cmptMat/modifyT")
    public String modifyT(@RequestParam("evaluation") int eval, @RequestParam("idskill") int idskill,
                          @RequestParam(value = "proiectId", required = false) int idproiect){

        UserExpleo user = userService.getUserExpleoPrincipal();

        userSkillService.saveUserSkill(user.getId(), idskill, eval);

        return "redirect:/retex/cmptMat?proiectId=" + idproiect;
    }


}
