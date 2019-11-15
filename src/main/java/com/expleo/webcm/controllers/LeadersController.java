package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
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

        return "leaders";
    }

    @GetMapping("/addNewProject")
    public String addNewProject(Model model){

        Proiect newProject = new Proiect();
        model.addAttribute("newProject", newProject);

        return "leadersAddNewProject";
    }

    @PostMapping("/addProject")
    public String addProjectToDb(@ModelAttribute("newProject") Proiect proiect){

        UserExpleo user = userService.getUserExpleoPrincipal();
        proiect.setManager(user.getNumarMatricol());

        proiectService.saveNewProject(proiect);

        return "redirect:/retex/leaders";
    }

    @GetMapping("/{codProiect}/detaliiProiect")
    public ModelAndView detaliiProiect(@PathVariable String codProiect, ModelMap model){
        ModelAndView mav = new ModelAndView();

        Proiect proiect = proiectService.findProjectByCodProiect(codProiect);
        List<UserExpleo> users = proiect.getUsers();
        List<Skill> skills = proiect.getSkills();

        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        mav.addAllObjects(model);

        mav.setViewName("detaliiProiect");

        return mav;
    }

    @GetMapping("/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(){
        return "addEmpToProj";
    }

    @PostMapping("/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(@RequestParam("cauta") String cauta,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){
        boolean hasProject = false;
//        UserExpleo userExpleo = userService.getUserExpleoPrincipal();
//        Proiect proiect = proiectService.findProjectByCodProiect(codProiect);
        List<UserExpleo> resultList = searchService.searchUser(cauta);

//        List<Proiect> userProjects = userExpleo.getProiecte();
        for(UserExpleo temp:resultList){
            for(Proiect tempProiect: temp.getProiecte()){
                if(tempProiect.getCodProiect().equals(codProiect)){
                    hasProject = true;
                    break;
                }

            }
        }


        System.out.println("==============================================================         " + hasProject);
        model.addAttribute("result", resultList);
        model.addAttribute("hasProject", hasProject);

        return "addEmpToProj";
    }

    @PostMapping("/{codProiect}/adaugaColaboratori/add")
    public String adaugaColaboratoriAdd(@PathVariable("codProiect") String codProiect)
    {

        return "redirect:/";
    }
}
