package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.helper.CreatePdf;
import com.expleo.webcm.helper.Principal;
import com.expleo.webcm.helper.ValidateResponse;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.SearchService;
import com.expleo.webcm.service.UserService;
import com.expleo.webcm.service.UserSkillService;
import com.expleo.webcm.service.*;

import java.io.*;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    private static final int[] INTERVAL_TARGET = {1,2,3,4};

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

        List<Proiect> projects = proiectService.findManagerProjects(Principal.getPrincipal());
        model.addAttribute("projects", projects);

        return "leaders_home";
    }


    @GetMapping("/searchPeople")
    public String showSearchPeople(){
        return "searchPeople";
    }

    @GetMapping("/searchPeople/search")
    public String searchPeopleByEvaluation(@RequestParam(value = "searchTerm") String text,@RequestParam("evaluation") int eval, Model theModel){

        List<UserSkill> userSkillsLast = searchService.searchSkillWithEvaluation(text, eval);
        theModel.addAttribute("usersSkills", userSkillsLast);

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

    @PostMapping(value = "/addProject", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public Object addProjectToDb(@Valid @ModelAttribute("newProject") Proiect proiect, BindingResult result){
        ValidateResponse validateResponse = new ValidateResponse();

        if(result.hasErrors()){
//            Map<String, String> errors = result.getFieldErrors().stream()
//                    .collect(
//                            Collectors.toMap(FieldError::getDefaultMessage, FieldError::getField)
//                    );
            Map<String, String> errors = new LinkedHashMap<>();
            for(FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }

            System.out.println(errors);
            System.out.println(errors.size());
            validateResponse.setValidated(false);
            validateResponse.setErrorMessages(errors);
        }else {
            System.out.println("Ajunge aici");

//            UserExpleo user = userService.getUserExpleoPrincipal();
            proiect.setCodProiect(proiect.getCodProiect().toUpperCase());
            proiect.setManager(Principal.getPrincipal());

            proiectService.saveNewProject(proiect);
            validateResponse.setValidated(true);
            System.out.println("*****************************************************");
        }
        return validateResponse;
    }

    @GetMapping("/project/{codProiect}")
    public String detaliiProiect(@PathVariable String codProiect, ModelMap model){

        List<UserExpleo> users = new LinkedList<>();
        List<ProiectSkill> skills = new LinkedList<>();

        Proiect proiect = proiectService.getProjectListsUsersSkills(codProiect, users, skills);


        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        model.addAttribute("varPath", codProiect);
        model.addAttribute("intervalPondere", INTERVAL_PONDERE);
        model.addAttribute("intervalTarget", INTERVAL_TARGET);

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
    @ResponseBody
    public void removeEmpFromProject(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("userId") Integer userId)
    {
        proiectService.removeUserFromProject(codProiect, userId);

        System.out.println("//////////////////////////////////////////////////////////codProiect = " + codProiect);

        //return "redirect:/webCM/leaders/project/"+codProiect;
    }

    @PostMapping(value = "/project/{codProiect}/renuntaLaProiect")
    public @ResponseBody String renuntaLaProiect(@PathVariable("codProiect") String codProiect)
    {
        proiectService.dropTheProject(codProiect);

        return "ceva";
    }

    @GetMapping("/freeProjects")
    public String freeProjects(Model model){
        List<Proiect> result = proiectService.getFreeProjects();
        model.addAttribute("result", result);
        
        return "leaders_freeProjects";
    }

    @GetMapping("/freeProjects/add")
    public void addFreeProject(@RequestParam("codProiect") String codProiect)
    {

        proiectService.addFreeProject(codProiect, Principal.getPrincipal());

        System.out.println("codProiect = " + codProiect);

//        return "redirect:/webCM/leaders/freeProjects";
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
                             @RequestParam("value") Integer pondere) {

        proiectService.setPondere(codProiect, skillId, pondere);

        return "redirect:/webCM/leaders/project/"+codProiect;
    }

    @GetMapping("/project/{codProiect}/setTarget")
    public String setTarget(@PathVariable("codProiect") String codProiect,
                             @RequestParam("skillId") Integer skillId,
                             @RequestParam("value") Integer target) {

        proiectService.setTarget(codProiect, skillId, target);

        return "redirect:/webCM/leaders/project/"+codProiect;
    }

    @GetMapping("/selectMatrix")
    public String matrice(ModelMap modelMap){

        List<Proiect> proiecte = proiectService.findManagerProjects(Principal.getPrincipal());

//        model.addAttribute("proiecte", proiecte);

        return matrice(proiecte.get(0).getCodProiect(), modelMap);
    }

    @PostMapping("/project/{codProiect}/matrix")
    public String matrice(@PathVariable("codProiect") String codProiect, ModelMap model){

        List<Proiect> proiecte = proiectService.findManagerProjects(Principal.getPrincipal());

        model.addAttribute("proiecte", proiecte);

        Proiect proiect = proiectService.findProjectByCodProiect(codProiect);

        List<UserExpleo> userExpleos = proiect.getUsers();

        model.addAttribute("users", userExpleos);

        System.out.println("codProiect = " + codProiect);

        return "dropdownMatrix";
    }
}
