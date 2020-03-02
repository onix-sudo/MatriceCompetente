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

/**
 * The Spring Controller for leaders
 */

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



    /**
     * Get method that handles the request mapping below
     * @param model pass to the jsp file the list of projects available to the user
     * @return the jsp file from view directory
     */
    @GetMapping
    public String showLeaders(Model model){

        List<Proiect> projects = proiectService.findManagerProjects(Principal.getPrincipal());
        model.addAttribute("projects", projects);

        return "leaders_home";
    }
    /**
     * Get method that handles the mapping of the request below to search a user form
     * @return the jsp file from view directory
     */
    @GetMapping("/searchPeople")
    public String showSearchPeople(){
        return "searchPeople";
    }

    /**
     * Get method that handles the mapping of the request below to search a user
     * @param text the search term typed by the user
     * @param eval the minimum value of evaluation for the users shown
     *             The parameters above are used by the search service in order to retrieve the suitable list .
     * @param theModel pass to the jsp the search result
     * @return the jsp file from view directory
     */
    @GetMapping("/searchPeople/search")
    public String searchPeopleByEvaluation(@RequestParam(value = "searchTerm") String text,@RequestParam("evaluation") int eval, Model theModel){

        List<UserSkill> userSkillsLast = searchService.searchSkillWithEvaluation(text, eval);
        theModel.addAttribute("usersSkills", userSkillsLast);

        return "searchPeople";
    }

    /**
     * Post method that handles the mapping of the request below to download the PDF created by the showSearchPeople method
     * @param text the search term typed by the user, this approach helps us to introduce the value in the PDF file
     * @param evaluation the minimum value for evaluation, this approach helps us to introduce the value in the PDF file
     * @param response produce the download form
     */
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

    /**
     * Get method that handles the mapping of the request below to add a new project form
     * @param model pass to the jsp file a Proiect object in order to provide the fields required to register
     *                 a new project
     * @return the jsp file from view directory
     */
    @GetMapping("/addNewProject")
    public String addNewProject(Model model){

        Proiect newProject = new Proiect();
        model.addAttribute("newProject", newProject);

        return "leaders_leadersAddNewProject";
    }

    /**
     * Post method that handles the mapping of the request below to save a new project
     * @param proiect the object to be added into the database
     * @param result check if the fields are correct and if so, the object will be added in the database
     * @return the jsp file from view directory
     */
    @PostMapping(value = "/addProject", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ValidateResponse addProjectToDb(@Valid @ModelAttribute("newProject") Proiect proiect, BindingResult result){
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

    /**
     * Get method that handles the mapping of the request below to show the project details
     * @param codProiect the project code related to the current request
     * @param model pass to the jsp file the elements for the matrix and project details
     * @return the jsp file from view directory
     */
    @GetMapping("/project/{codProiect}")
    public String detaliiProiect(@PathVariable String codProiect, ModelMap model){
        List<UserExpleo> foundUsers = new LinkedList<>();
        List<ProiectSkill> foundSkills = new LinkedList<>();
        List<UserSkill> foundUserSkills = new LinkedList<>();
        CreateMatrixTeam createMatrixTeam = new CreateMatrixTeam();

        proiectService.findProjectUsersAndSkills(codProiect, foundUsers, foundSkills, foundUserSkills);
        List<MatrixTeamMember> matrixTeam = createMatrixTeam.makeMatrixTeamList(foundUsers,foundSkills,foundUserSkills);
        createMatrixTeam.sortMatrixTeamList(matrixTeam);

        model.addAttribute("project", proiectService.getProjectByCodProiect(codProiect));
        model.addAttribute("varPath", codProiect);
        model.addAttribute("matrixTeam", matrixTeam);

        return "leaders_detaliiProiect";

    }

    /**
     * Get method that handles the mapping of the request below to show the user form, for the current project
     * @param codProiect the project code related to the current request
     * @param model pass to the jsp file the elements for adding a new user in the project
     * @return the jsp file from view directory
     */
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

    /**
     * Post method that handles the mapping of the request below to show the user form, for the current project by a defined search term
     * @param searchTerm the search term used to show the users you want
     * @param codProiect the project code related to the current request
     * @param model pass to the jsp file the list of search results
     * @return the jsp file from view directory
     */
    @PostMapping(value = "/project/{codProiect}/adaugaColaboratori")
    public String adaugaColaboratoriView(@RequestParam("searchTerm") String searchTerm,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){

        List<UserExpleo> foundUsers = searchService.searchUsersNotInProject(codProiect, searchTerm.trim());
        model.addAttribute("result", foundUsers);
        return adaugaColaboratoriView(codProiect, model);
    }

    /**
     * Post method that handles the mapping of the request below to add the user in the current project
     * @param codProiect the project code related to the current request
     * @param userId the id related to the user that will be added in the project
     */
    @PostMapping("/project/{codProiect}/adaugaColaboratori/add")
    @ResponseBody
    public void adaugaColaboratoriAdd(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("userId") Integer userId)
    {
        proiectService.addUserToProject(codProiect, userId);
    }

    /**
     * Post method that handles the mapping of the request below to remove the user from the current project
     * @param codProiect the project code related to the current request
     * @param userId the id related to the user that will be removed from the current project
     */
    @PostMapping("/project/{codProiect}/removeEmp")
    @ResponseBody
    public void removeEmpFromProject(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("userId") Integer userId)
    {
        proiectService.removeUserFromProject(codProiect, userId);
    }

    /**
     * Post method that handles the mapping of the request below to give up the project
     * @param codProiect the project code related to the current request
     * @return the jsp file from view directory
     */
    @PostMapping(value = "/project/{codProiect}/renuntaLaProiect")
    public @ResponseBody String renuntaLaProiect(@PathVariable("codProiect") String codProiect)
    {
        proiectService.dropTheProject(codProiect);
        return "ceva";
    }

    /**
     * Get method that handles the mapping of the request below to show the available projects
     * @param model pass to the jsp file the list of available projects
     * @return the jsp file from view directory
     */
    @GetMapping("/freeProjects")
    public String freeProjects(Model model){
        List<Proiect> result = proiectService.getFreeProjects();
        model.addAttribute("result", result);

        return "leaders_freeProjects";
    }

    /**
     * Get method that handles the mapping of the request below to add a new available project
     * @return the jsp file from view directory
     */
    @GetMapping("/freeProjects/add")
    public void addFreeProject(@RequestParam("codProiect") String codProiect)
    {
        proiectService.addFreeProject(codProiect, Principal.getPrincipal());
    }

    /**
     * Get method that handles the mapping of the request below to show the skill form
     * @param codProiect the project code related to the current request
     * @param model pass to the jsp file the elements for adding a new skill in the current project
     * @return the jsp file from view directory
     */
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

    /**
     * Get method that handles the mapping of the request below to show the skill form
     * @param searchTerm the search term used to show the skills
     * @param codProiect the project code related to the current request
     * @param model pass to the jsp file the elements for adding a new skill in the current project
     * @return the jsp file from view directory
     */
    @PostMapping("/project/{codProiect}/addSkills")
    public String addSkillsView(@RequestParam(required = false, name = "searchTerm") String searchTerm,
                                         @PathVariable ("codProiect") String codProiect, ModelMap model){
        List<Skill> foundSkills = searchService.searchSkillsNotInProject(codProiect, searchTerm.trim());
        model.addAttribute("search", searchTerm.trim());
        model.addAttribute("result", foundSkills);

        return addSkillsView(codProiect, model);
    }

    /**
     * Post method that handles the mapping of the request below to add a new skills to the current project
     * @param codProiect the project code related to the current request
     * @param skillId the id related to the skill that will be added in the project
     * @param model pass to the jsp file the elements for adding a new skill in the current project
     */
    @PostMapping("/project/{codProiect}/add")
    public void addSkillsAdd(@PathVariable("codProiect") String codProiect,
                                        @RequestParam("skillId") Integer skillId, ModelMap model)
    {
        proiectService.addSkillToProject(codProiect, skillId);
    }

    /**
     * Post method that handles the mapping of the request below to remove a skill from the current project
     * @param codProiect the project code related to the current request
     * @param skillId the id related to the skill that will be removed from the project
     * @return redirect to the jsp file from view directory
     */
    @PostMapping("/project/{codProiect}/removeSkill")
    public String removeSkillFromProject(@PathVariable("codProiect") String codProiect,
                                       @RequestParam("skillId") Integer skillId)
    {
        proiectService.removeSkillFromProject(codProiect, skillId);
        return "redirect:/webCM/leaders/project/"+codProiect;
    }

    /**
     * Get method that handles the mapping of the request below to change the current project weights
     * @param codProiect the project code related to the current request
     * @param pondere the value of the set weight
     * @param skillId the id related to the skill that will be changed regarding weight
     * @return redirect to the jsp file from view directory
     */
    @GetMapping("/project/{codProiect}/setPondere")
    public void setPondere(@PathVariable("codProiect") String codProiect,
                             @RequestParam("skillId") Integer skillId,
                             @RequestParam("value") Integer pondere) {

        proiectService.setPondere(codProiect, skillId, pondere);
    }

    /**
     * Get method that handles the mapping of the request below to change the current project targets
     * @param codProiect the project code related to the current request
     * @param target the value of the set target
     * @param skillId the id related to the skill that will be changed regarding target
     * @return redirect to the jsp file from view directory
     */
    @GetMapping("/project/{codProiect}/setTarget")
    public void setTarget(@PathVariable("codProiect") String codProiect,
                             @RequestParam("skillId") Integer skillId,
                             @RequestParam("value") Integer target) {

        proiectService.setTarget(codProiect, skillId, target);
    }
}
