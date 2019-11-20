package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.SearchService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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


    @GetMapping("/searchPeople")
    public String showSearchPeople(Model theModel){

        List<Proiect> projects = proiectService.findManagerProjects(userService.getUserExpleoPrincipal());

        Skill theSkill = new Skill();

        theModel.addAttribute("skill", theSkill);

        return "searchPeople";
    }

    @GetMapping("/searchPeople/search")
    public String showLeaders(@RequestParam(value = "searchTerm") String text, Model theModel){

//        List<Proiect> projects = proiectService.findManagerProjects(userService.getUserExpleoPrincipal());
//        theModel.addAttribute("projects", projects);
//
//        UserExpleo user = userService.getUserExpleoPrincipal();

        List<Skill> searchResult = searchService.searchSkill(text);

        theModel.addAttribute("result", searchResult);

        System.out.println("searchResult = " + searchResult);

        Set<UserExpleo> userExpleos = new HashSet<>();

        for (Skill skill : searchResult){
            userExpleos.addAll(skill.getUsers());
        }

        Iterator<Skill> itSkill = searchResult.iterator();

        Iterator<UserExpleo> itUserExpleo = userExpleos.iterator();

        while (itSkill.hasNext() && itUserExpleo.hasNext()){
            Skill skill = itSkill.next();

            UserExpleo userExpleo = itUserExpleo.next();

            UserSkill userSkill = new UserSkill(skill,userExpleo);

            System.out.println("userSkill = " + userSkill);

            System.out.println("skillAAAAAAAAAAAAAAAA = " + skill);
        }

        System.out.println("userExpleos = " + userExpleos);

        theModel.addAttribute("users", userExpleos);

        return "searchPeople";
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

        Proiect proiect = proiectService.findProjectByCodProiect(codProiect);
        List<UserExpleo> users = proiect.getUsers();
        List<ProiectSkill> skills = proiectService.showSkillsforProject(proiect.getProiectId());

        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        model.addAttribute("varPath",codProiect);
        mav.addAllObjects(model);

        mav.setViewName("leaders_detaliiProiect");

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
        for(UserExpleo userExpleo:pickedUsers){
            resultList.remove(userExpleo);
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
        proiectService.removeUserFromProject(
                proiectService.findProjectByCodProiect(codProiect).getProiectId(), userId);

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
        boolean hasSkill = false;
        List<Skill> resultList = searchService.searchSkill(cauta);
        List<ProiectSkill> proiectSkills = proiectService.findProjectByCodProiect(codProiect).getSkills();
        List<Skill> pickedSkill = new ArrayList<>();

        if(!resultList.isEmpty()) {
            for (ProiectSkill tempProjSkill : proiectSkills) {
                for(Skill skillFromResult : resultList) {
                    if (tempProjSkill.getSkill().getNumeSkill().equals(skillFromResult.getNumeSkill())) {
                        pickedSkill.add(skillFromResult);
                    }
                }
            }
        }
        for(Skill skill:pickedSkill){
            resultList.remove(skill);
        }


        model.addAttribute("result", resultList);
        model.addAttribute("hasSkill", hasSkill);
        model.addAttribute("varPath", codProiect);

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
