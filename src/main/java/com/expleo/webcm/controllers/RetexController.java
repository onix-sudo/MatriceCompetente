package com.expleo.webcm.controllers;

import com.expleo.webcm.dao.ProiectDAO;
import com.expleo.webcm.dao.SkillDAO;
import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/retex")
public class RetexController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProiectService proiectService;

    @GetMapping
    public String retex(ModelMap model){
        List<Proiect> proiectList = proiectService.findProjectByEmail(userService.getUserExpleoPrincipal());
        model.addAttribute("proiectList", proiectList);

        return "retex";
    }

    @GetMapping(value = "/cmptMat")
    public String competencyMatrix(ModelMap model, @RequestParam(name = "proiectId") Integer proiectId) {
        List<Skill> skills = proiectService.showSkillsforProject(proiectId);
        model.addAttribute("skillList", skills);

        return "cmptMat";
    }
}
