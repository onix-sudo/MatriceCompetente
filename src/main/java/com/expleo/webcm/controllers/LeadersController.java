package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/retex/leaders")
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

        return "leaders_leaders";
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

        return "redirect:/retex/leaders";
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
    public String adaugaColaboratoriView(){
        return "leaders_addEmpToProj";
    }

    @PostMapping("/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(@RequestParam("cauta") String cauta,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){
        boolean hasProject = false;
        List<UserExpleo> resultList = searchService.searchUser(cauta);

//        Iterator<UserExpleo> iter = resultList.iterator();

        if(resultList != null) {
            for (UserExpleo user : resultList) {
                UserExpleo temp = user;
                for (Proiect tempProiect : temp.getProiecte()) {
                    if (resultList.size() == 1 && tempProiect.getCodProiect().equals(codProiect)) {
                        resultList = null;
                    } else if (tempProiect.getCodProiect().equals(codProiect)) {
                        resultList.remove(temp);
                    }

                }
            }
        }
//        for(UserExpleo ue:resultList){
//            System.out.println(ue);
//        }
//        System.out.println(resultList.isEmpty());
//        System.out.println(resultList.size());


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
        return "redirect:/retex/leaders/"+codProiect;
    }

    @PostMapping("/{codProiect}/removeEmp")
    public String removeEmpFromProject(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("userId") Integer userId)
    {
        proiectService.removeUserFromProject(
                proiectService.findProjectByCodProiect(codProiect).getProiectId(), userId);

        return "redirect:/retex/leaders/"+codProiect;
    }

    @PostMapping("/{codProiect}/renuntaLaProiect")
    public String renuntaLaProiect(@PathVariable("codProiect") String codProiect)
    {

        proiectService.dropTheProject(codProiect);

        return "redirect:/retex/leaders/";
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

        return "redirect:/retex/leaders/freeProjects";
    }

    @GetMapping("/{codProiect}/addSkills")
    public String addSkillsView(){
        return "leaders_addSkillsToProj";
    }

    @PostMapping("/{codProiect}/addSkills")
    public String addSkillsView(@RequestParam("cauta") String cauta,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){
        boolean hasSkill = false;
        List<Skill> resultList = searchService.searchSkill(cauta);
        List<ProiectSkill> proiectSkills = proiectService.findProjectByCodProiect(codProiect).getSkills();

//        for(ProiectSkill test: proiectSkills){
//            System.out.println(test.getSkill().getNumeSkill());
//        }

        Iterator<Skill> iter = resultList.iterator();

        if(resultList != null) {
            for (ProiectSkill tempProjSkill : proiectSkills) {
                while (iter.hasNext()) {
//                System.out.println("*************************************************************************");
//                System.out.println(tempProjSkill.getSkill().getNumeSkill());
//                System.out.println(tempSkill.getNumeSkill());
//                System.out.println(tempProjSkill.getSkill().getNumeSkill().equals(tempSkill.getNumeSkill()));
//                System.out.println("*************************************************************************");
                    Skill skill = iter.next();
                    if (resultList.size() == 1 && tempProjSkill.getSkill().getNumeSkill().equals(skill.getNumeSkill())) {
                        resultList = null;
                    } else if (tempProjSkill.getSkill().getNumeSkill().equals(skill.getNumeSkill())) {
                        iter.remove();
                    }
                }
            }
        }


//        System.out.println("====================================" + hasSkill);

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
        return "redirect:/retex/leaders/"+codProiect;
    }

    @PostMapping("/{codProiect}/removeSkill")
    public String removeSkillFromProject(@PathVariable("codProiect") String codProiect,
                                       @RequestParam("skillId") Integer skillId)
    {
        proiectService.removeSkillFromProject(codProiect, skillId);

        return "redirect:/retex/leaders/"+codProiect;
    }
}
