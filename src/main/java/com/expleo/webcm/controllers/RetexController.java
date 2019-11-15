package com.expleo.webcm.controllers;

import com.expleo.webcm.dao.SkillDAO;
import com.expleo.webcm.dao.UserSkillDAO;
import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;
import com.expleo.webcm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<Skill> skills = proiectService.showSkillsforProject(proiectId);
        model.addAttribute("skillList", skills);

        return "cmptMat";
    }

    //        List<Skill> skills = skillDAO.getSkills();
//
//        UserExpleo user = userService.getUserExpleoPrincipal();
//
//        System.out.println(user);
//
//        model.addAttribute("skill", skills);
    //        for (Iterator<UserSkill> iterator = userSkills.iterator(); iterator.hasNext();){
//            UserSkill userSkill = iterator.next();
//
//            if (userSkill.getUser().equals(user) && userSkill.getSkill().equals(this)){
//
//                System.out.println("userSkill = " + userSkill);
//
//                userSkill.getUser().getSkills();}
//
//        }
    @GetMapping("/personalProfile")
    public String personalProfile(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();

        List<UserSkill> userSkills = userSkillService.getUserSkillByUser(user);

        model.addAttribute("userSkills", userSkills);

        return "personalProfile";
    }

    @GetMapping("/personalProfile/showFormForAddSkill")
    public String showFormForAddSkill(ModelMap model){

        Skill theSkill = new Skill();

        model.addAttribute("skill", theSkill);

        return "skill-form";
    }

    @GetMapping("/personalProfile/showFormForAddSkill/search")
    public String searchSkills(@RequestParam(value = "searchTerm") String text, Model theModel){

        List<Skill> searchResult = searchService.searchSkill(text);
        theModel.addAttribute("result", searchResult);

        return "skill-form";
    }

    @GetMapping("/personalProfile/showFormForAddSkill/search/addSkillToUser")
    public String addSkilltoUser(@RequestParam(value = "skillId") int skillId, Model theModel){

        Skill skill = skillService.getSkill(skillId);

        UserExpleo user = userService.getUserExpleoPrincipal();

        UserSkill userSkill = new UserSkill(skill, user);


        userSkillService.saveUserSkill(userSkill);
//

        System.out.println("skill = " + skill);


        return "redirect:/retex/personalProfile";
    }

    @GetMapping("/deleteSkill")
    public String deleteSkill(@RequestParam("skillId") int theId){
        skillService.deleteSkill(theId);
        return "redirect:/skill/list";
    }

}
