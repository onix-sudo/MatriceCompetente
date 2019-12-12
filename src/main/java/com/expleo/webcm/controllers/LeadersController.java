package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.helper.*;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

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
            Map<String, String> errors = new LinkedHashMap<>();
            for(FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }

            validateResponse.setValidated(false);
            validateResponse.setErrorMessages(errors);
        }else {
            proiect.setCodProiect(proiect.getCodProiect().toUpperCase());
            proiect.setManager(Principal.getPrincipal());

            proiectService.saveNewProject(proiect);
            validateResponse.setValidated(true);
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
        List<UserExpleo> users = new LinkedList<>();
        List<ProiectSkill> skills = new LinkedList<>();

        Proiect proiect = proiectService.getProjectListsUsersSkills(codProiect, users, skills);


        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        model.addAttribute("varPath", codProiect);
        model.addAttribute("intervalPondere", INTERVAL_PONDERE);
        model.addAttribute("intervalTarget", INTERVAL_TARGET);
        return "leaders_addEmpToProj";
    }

    @PostMapping(value = "/project/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(@RequestParam("searchTerm") String searchTerm,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){

        List<UserExpleo> foundUsers = searchService.searchUsersNotInProject(codProiect, searchTerm.trim());

        List<UserExpleo> users = new LinkedList<>();
        List<ProiectSkill> skills = new LinkedList<>();

        Proiect proiect = proiectService.getProjectListsUsersSkills(codProiect, users, skills);


        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        model.addAttribute("varPath", codProiect);
        model.addAttribute("intervalPondere", INTERVAL_PONDERE);
        model.addAttribute("intervalTarget", INTERVAL_TARGET);

        model.addAttribute("result", foundUsers);
        model.addAttribute("searchTermUser", searchTerm);
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
    public void addFreeProject(@RequestParam("codProiect") String codProiect, ModelMap model)
    {
        proiectService.addFreeProject(codProiect, Principal.getPrincipal());
    }

    @GetMapping("/project/{codProiect}/addSkills")
    public String addSkillsView(@PathVariable ("codProiect") String codProiect, ModelMap model){
        List<UserExpleo> users = new LinkedList<>();
        List<ProiectSkill> skills = new LinkedList<>();

        Proiect proiect = proiectService.getProjectListsUsersSkills(codProiect, users, skills);

        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        model.addAttribute("varPath", codProiect);
        model.addAttribute("intervalPondere", INTERVAL_PONDERE);
        model.addAttribute("intervalTarget", INTERVAL_TARGET);
        return "leaders_addSkillsToProj";
    }

    @PostMapping("/project/{codProiect}/addSkills")
    public String addSkillsView(@RequestParam(required = false, name = "searchTerm") String searchTerm,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){
        List<UserExpleo> users = new LinkedList<>();
        List<ProiectSkill> skills = new LinkedList<>();

        Proiect proiect = proiectService.getProjectListsUsersSkills(codProiect, users, skills);

        model.addAttribute("users", users);
        model.addAttribute("skills", skills);
        model.addAttribute("project", proiect);
        model.addAttribute("varPath", codProiect);
        model.addAttribute("intervalPondere", INTERVAL_PONDERE);
        model.addAttribute("intervalTarget", INTERVAL_TARGET);
        List<Skill> foundSkills = searchService.searchSkillsNotInProject(codProiect, searchTerm.trim());

        model.addAttribute("search", searchTerm);
        model.addAttribute("result", foundSkills);

        return "leaders_addSkillsToProj";
    }

    @PostMapping("/project/{codProiect}/add")
    public void addSkillsAdd(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("skillId") Integer skillId, ModelMap model)
    {
        proiectService.addSkillToProject(codProiect, skillId);
    }

    @PostMapping("/project/{codProiect}/removeSkill")
    public String removeSkillFromProject(@PathVariable("codProiect") String codProiect,
                                       @RequestParam("skillId") Integer skillId)
    {
        proiectService.removeSkillFromProject(codProiect, skillId);
        return "redirect:/webCM/leaders/project/"+codProiect;
    }

    @GetMapping("/project/{codProiect}/setPondere")
    public void setPondere(@PathVariable("codProiect") String codProiect,
                             @RequestParam("skillId") Integer skillId,
                             @RequestParam("value") Integer pondere) {

        proiectService.setPondere(codProiect, skillId, pondere);
    }

    @GetMapping("/project/{codProiect}/setTarget")
    public void setTarget(@PathVariable("codProiect") String codProiect,
                             @RequestParam("skillId") Integer skillId,
                             @RequestParam("value") Integer target) {

        proiectService.setTarget(codProiect, skillId, target);
    }

    @GetMapping("/project/{codProiect}/matrix")
    public String matrice(@PathVariable("codProiect") String codProiect, ModelMap model){

        List<UserExpleo> foundUsers = new LinkedList<>();
        List<ProiectSkill> foundSkills = new LinkedList<>();
        List<UserSkill> foundUserSkills = new LinkedList<>();

        proiectService.findProjectUsersAndSkills(codProiect, foundUsers, foundSkills, foundUserSkills);

        CreateMatrixTeam createMatrixTeam = new CreateMatrixTeam();
        List<MatrixTeamMember> matrixTeam = createMatrixTeam.makeMatrixTeamList(foundUsers,foundSkills,foundUserSkills);
        createMatrixTeam.sortMatrixTeamList(matrixTeam);

        model.addAttribute("foundSkills", foundSkills);

        model.addAttribute("matrixTeam", matrixTeam);

        return "dropdownMatrix";
    }
}
