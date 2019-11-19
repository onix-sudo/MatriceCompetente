package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.SearchService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/webCM/leaders")
public class LeadersController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProiectService proiectService;

    @Autowired
    private SearchService searchService;

    @GetMapping
    public String showLeaders(Model model){

        List<Proiect> projects = proiectService.findManagerProjects(userService.getUserExpleoPrincipal());
        model.addAttribute("projects", projects);

        return "leaders_home";
    }

    @GetMapping("/addNewProject")
    public String addNewProject(Model model){

        Proiect newProject = new Proiect();
        model.addAttribute("newProject", newProject);

        return "leaders_leadersAddNewProject";
    }

    @PostMapping("/addProject")
    public String addProjectToDb(@ModelAttribute("newProject") Proiect proiect){

        UserExpleo user = userService.getUserExpleoPrincipal();
        proiect.setManager(user.getNumarMatricol());

        proiectService.saveNewProject(proiect);

        return "redirect:/webCM/leaders";
    }

    @GetMapping("/{codProiect}")
    public ModelAndView detaliiProiect(@PathVariable String codProiect, ModelMap model){
        ModelAndView mav = new ModelAndView();
        System.out.println("********************************************************************************");
        Proiect proiect = new Proiect();
        System.out.println("111111111111111111111111111111111111111111111111111111111111111111111111111111111");

        List<UserExpleo> users = new ArrayList<>();
        System.out.println("2222222222222222222222222222222222222222222222222222222222222222222222222222222222");

//        List<ProiectSkill> skills = proiect.getSkills();
        List<Skill> skills = new ArrayList<>();
        System.out.println("3333333333333333333333333333333333333333333333333333333333333333333333333333333333");

        proiectService.findProjectByCodProiect(codProiect, proiect, users, skills);


        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        model.addAttribute("varPath", codProiect);
        mav.addAllObjects(model);

        mav.setViewName("leaders_detaliiProiect");
        System.out.println("********************************************************************************");
        return mav;
    }

    @GetMapping("/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(@PathVariable ("codProiect") String codProiect, ModelMap model){
        model.addAttribute("varPath", codProiect);
        return "leaders_addEmpToProj";
    }

    @PostMapping("/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(@RequestParam("cauta") String cauta,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){
        boolean hasProject = false;
        List<UserExpleo> resultList = searchService.searchUser(cauta);
        List<UserExpleo> pickedUsers = new ArrayList<>();

        if(!resultList.isEmpty()) {
            for (UserExpleo user : resultList) {
                for (Proiect tempProiect : user.getProiecte()) {
                    if (tempProiect.getCodProiect().equals(codProiect)) {
                        pickedUsers.add(user);
                    }
                }
            }
        }
        if(resultList.size()==pickedUsers.size()) {
            resultList=null;
        } else {
            resultList.removeAll(pickedUsers);
        }

        model.addAttribute("result", resultList);
        model.addAttribute("hasProject", hasProject);
        model.addAttribute("varPath", codProiect);

        return "leaders_addEmpToProj";
    }

    @PostMapping("/{codProiect}/adaugaColaboratori/add")
    public String adaugaColaboratoriAdd(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("userId") Integer userId)
    {
        proiectService.addUserToProject(codProiect, userId);
        return "redirect:/webCM/leaders/"+codProiect;
    }

    @PostMapping("/{codProiect}/removeEmp")
    public String removeEmpFromProject(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("userId") Integer userId)
    {
        proiectService.removeUserFromProject(codProiect, userId);

        return "redirect:/webCM/leaders/"+codProiect;
    }

    @PostMapping("/{codProiect}/renuntaLaProiect")
    public String renuntaLaProiect(@PathVariable("codProiect") String codProiect)
    {

        proiectService.dropTheProject(codProiect);

        return "redirect:/webCM/leaders/";
    }

    @GetMapping("/freeProjects")
    public String freeProjects(Model model){

        List<Proiect> result = proiectService.getFreeProjects();
        model.addAttribute("result", result);

        return "leaders_freeProjects";
    }

    @PostMapping("/freeProjects/add")
    public String addFreeProject(@RequestParam("codProiect") String codProiect)
    {
        UserExpleo principal = userService.getUserExpleoPrincipal();
        proiectService.addFreeProject(codProiect, principal);

        return "redirect:/webCM/leaders/freeProjects";
    }

    @GetMapping("/{codProiect}/addSkills")
    public String addSkillsView(@PathVariable ("codProiect") String codProiect, ModelMap model){
        model.addAttribute("varPath", codProiect);
        return "leaders_addSkillsToProj";
    }

    @PostMapping("/{codProiect}/addSkills")
    public String addSkillsView(@RequestParam("cauta") String cauta,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){
//        boolean hasSkill = false;
        System.out.println("============================================================================");
        System.out.println("============================================================================");
        List<Skill> resultList = searchService.searchSkill(cauta);
        System.out.println("----------------------------------------------------------------------------");
        List<ProiectSkill> proiectSkills = proiectService.findProjectSkillsByCodProiect(codProiect);
        List<Skill> pickedSkill = new ArrayList<>();

        if(!resultList.isEmpty()) {
            for (ProiectSkill tempExistingSkill : proiectSkills) {
                for(Skill tempSearchedSkill : resultList) {
                    if (tempExistingSkill.getSkill().getNumeSkill().equals(tempSearchedSkill.getNumeSkill())) {
                        pickedSkill.add(tempSearchedSkill);
                    }
                }
            }
        }

        if(resultList.size()==pickedSkill.size()) {
            resultList=null;
        } else {
            resultList.removeAll(pickedSkill);
        }

        model.addAttribute("result", resultList);
//        model.addAttribute("hasSkill", hasSkill);
        model.addAttribute("varPath", codProiect);
        System.out.println("============================================================================");
        System.out.println("============================================================================");

        return "leaders_addSkillsToProj";
    }

    @PostMapping("/{codProiect}/addSkills/add")
    public String addSkillsAdd(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("skillId") Integer skillId)
    {
        proiectService.addSkillToProject(codProiect, skillId);
        return "redirect:/webCM/leaders/"+codProiect;
    }

    @PostMapping("/{codProiect}/removeSkill")
    public String removeSkillFromProject(@PathVariable("codProiect") String codProiect,
                                       @RequestParam("skillId") Integer skillId)
    {
        proiectService.removeSkillFromProject(codProiect, skillId);

        return "redirect:/webCM/leaders/"+codProiect;
    }
}
