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
    SearchService searchService;

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

        for(UserExpleo temp:resultList){
            for(Proiect tempProiect: temp.getProiecte()){
                if(tempProiect.getCodProiect().equals(codProiect)){
                    hasProject = true;
                    break;
                }

            }
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
        boolean hasProject = false;
        List<Skill> resultList = searchService.searchSkill(cauta);

        for(Skill temp:resultList){
            for(ProiectSkill tempProiect: temp.getProiecte()){
                if(tempProiect.getProiect().equals(codProiect)){
                    hasProject = true;
                    break;
                }

            }
        }


        model.addAttribute("result", resultList);
        model.addAttribute("hasProject", hasProject);
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
}
