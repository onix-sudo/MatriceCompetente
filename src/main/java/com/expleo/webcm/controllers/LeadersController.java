package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.helper.CreatePdf;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.SearchService;
import com.expleo.webcm.service.UserService;
import com.expleo.webcm.service.UserSkillService;

import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/webCM/leaders")
public class LeadersController {

    private static final int[] INTERVAL_PONDERE = {1,2,3,4,5,6,7,8,9,10};

    @Autowired
    private UserService userService;

    @Autowired
    private ProiectService proiectService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private UserSkillService userSkillService;

    @GetMapping
    public String showLeaders(Model model){

        List<Proiect> projects = proiectService.findManagerProjects(userService.getUserExpleoPrincipal());
        model.addAttribute("projects", projects);

        return "leaders_home";
    }


    @GetMapping("/searchPeople")
    public String showSearchPeople(){

//        List<Proiect> projects = proiectService.findManagerProjects(userService.getUserExpleoPrincipal());

//        Skill theSkill = new Skill();
//        theModel.addAttribute("skill", theSkill);

        return "searchPeople";
    }

    @GetMapping("/searchPeople/search")
    public String searchPeopleByEvaluation(@RequestParam(value = "searchTerm") String text,@RequestParam("evaluation") int eval, Model theModel){

//        List<Skill> searchResult = searchService.searchSkill(text);
        List<UserSkill> userSkillsLast = searchService.searchSkillWithEvaluation(text, eval);

        theModel.addAttribute("usersSkills", userSkillsLast);
//        theModel.addAttribute("result", searchResult);

        return "searchPeople";
    }

    @PostMapping("/pdfDownload")
    public void pdfDownload(@RequestParam("downloadSearchTerm") String text,
                              @RequestParam("downloadEvaluationTerm") String evaluation, HttpServletResponse response) throws IOException {

        List<UserSkill> userSkills = searchService.searchSkillWithEvaluation(text, Integer.parseInt(evaluation));
        ByteArrayInputStream input = new CreatePdf().getPdfAsByteArrayInputStream(userSkills,text,evaluation);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition","attachment;filename=RezultateCautare-"+text+".pdf");
        try(ServletOutputStream servletOutputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) >= 0) {
                servletOutputStream.write(buffer, 0, len);
                servletOutputStream.flush();
            }
        }
    }


    @GetMapping("/addNewProject")
    public String addNewProject(Model model){

        Proiect newProject = new Proiect();
        model.addAttribute("newProject", newProject);

        return "leaders_leadersAddNewProject";
    }

    @PostMapping(value = "/addProject", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String addProjectToDb(@Valid @ModelAttribute("newProject") Proiect proiect, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );
            return "leaders_leadersAddNewProject";
        }else {
            System.out.println("Ajunge aici");

            UserExpleo user = userService.getUserExpleoPrincipal();
            proiect.setCodProiect(proiect.getCodProiect().toUpperCase());
            proiect.setManager(user.getNumarMatricol());

            proiectService.saveNewProject(proiect);
            System.out.println("*****************************************************");
            return "ceva";
        }

    }

    @GetMapping("/project/{codProiect}")
    public String detaliiProiect(@PathVariable String codProiect, ModelMap model){
        ModelAndView mav = new ModelAndView();
        Proiect proiect = proiectService.findProjectByCodProiect(codProiect);

        List<UserExpleo> users = new LinkedList<>();
        List<ProiectSkill> skills = new LinkedList<>();

        proiectService.getProjectListsUsersSkills(codProiect, users, skills);

        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        model.addAttribute("varPath", codProiect);
        model.addAttribute("intervalPondere", INTERVAL_PONDERE);
        mav.addAllObjects(model);

//        mav.setViewName("leaders_detaliiProiect");
        return "leaders_detaliiProiect";
    }

    @GetMapping("/project/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(@PathVariable ("codProiect") String codProiect, ModelMap model){
        model.addAttribute("varPath", codProiect);
        return "leaders_addEmpToProj";
    }

    @PostMapping(value = "/project/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(@RequestParam("searchTerm") String searchTerm,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){

        List<UserExpleo> foundUsers = searchService.searchUsersNotInProject(codProiect, searchTerm.trim());


        System.out.println("///////////////////////////////////////////////////////////////");
        model.addAttribute("result", foundUsers);
        model.addAttribute("varPath", codProiect);

        System.out.println("foundUsers = " + foundUsers);

        return "leaders_addEmpToProj";
    }

    @PostMapping("/project/{codProiect}/adaugaColaboratori/add")
    public String adaugaColaboratoriAdd(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("userId") Integer userId)
    {
        proiectService.addUserToProject(codProiect, userId);
        return "redirect:/webCM/leaders/project/"+codProiect;
    }

    @PostMapping("/project/{codProiect}/removeEmp")
    public String removeEmpFromProject(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("userId") Integer userId)
    {
        proiectService.removeUserFromProject(codProiect, userId);

        return "redirect:/webCM/leaders/project/"+codProiect;
    }

    @PostMapping("/project/{codProiect}/renuntaLaProiect")
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

    @GetMapping("/project/{codProiect}/addSkills")
    public String addSkillsView(@PathVariable ("codProiect") String codProiect, ModelMap model){
        model.addAttribute("varPath", codProiect);
        return "leaders_addSkillsToProj";
    }

    @PostMapping("/project/{codProiect}/addSkills")
    public String addSkillsView(@RequestParam("searchTerm") String searchTerm,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){

        List<Skill> foundSkills = searchService.searchSkillsNotInProject(codProiect, searchTerm.trim());

        model.addAttribute("result", foundSkills);
        model.addAttribute("varPath", codProiect);

        return "leaders_addSkillsToProj";
    }

    @PostMapping("/project/{codProiect}/addSkills/add")
    public String addSkillsAdd(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("skillId") Integer skillId)
    {
        proiectService.addSkillToProject(codProiect, skillId);

        return "redirect:/webCM/leaders/project/"+codProiect;
    }

    @PostMapping("/project/{codProiect}/removeSkill")
    public String removeSkillFromProject(@PathVariable("codProiect") String codProiect,
                                       @RequestParam("skillId") Integer skillId)
    {
        proiectService.removeSkillFromProject(codProiect, skillId);

        return "redirect:/webCM/leaders/project/"+codProiect;
    }

    @GetMapping("/project/{codProiect}/setPondere")
    public String setPondere(@PathVariable("codProiect") String codProiect,
                             @RequestParam("skillId") Integer skillId,
                             @RequestParam("value") Integer pondere)
    {

        proiectService.setPondere(codProiect, skillId, pondere);

        return "redirect:/webCM/leaders/project/"+codProiect;
    }
}
