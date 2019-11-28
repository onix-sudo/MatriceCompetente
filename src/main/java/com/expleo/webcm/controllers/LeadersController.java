package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Proiect;
import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.Skill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.SearchService;
import com.expleo.webcm.service.UserService;
import com.expleo.webcm.service.UserSkillService;

import java.io.*;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.apache.lucene.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

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
    public String showSearchPeople(Model theModel){

        List<Proiect> projects = proiectService.findManagerProjects(userService.getUserExpleoPrincipal());

        Skill theSkill = new Skill();

        theModel.addAttribute("skill", theSkill);

        return "searchPeople";
    }

    @GetMapping("/searchPeople/search")
    public String searchPeopleByEvaluation(@RequestParam(value = "searchTerm") String text,@RequestParam("evaluation") int eval, Model theModel){

        List<Skill> searchResult = searchService.searchSkill(text);

        List<UserSkill> userSkillsLast = searchService.searchSkillWithEvaluation(text, eval);

        theModel.addAttribute("usersSkills", userSkillsLast);

        theModel.addAttribute("result", searchResult);

        return "searchPeople";
    }

    @GetMapping("/pdfDownload")
    public String pdfDownload(@RequestParam("downloadSearchTerm") String text,
                              @RequestParam("downloadEvaluationTerm") String evaluation, HttpServletResponse response) throws ServletException, IOException {

        List<UserSkill> userSkills = searchService.searchSkillWithEvaluation(text, Integer.parseInt(evaluation));

        //helper pentru pdf writer

        String dest = "C:/Users/vfbaldovin/Desktop/bla.pdf"; //configurat numele fisierului

        com.itextpdf.layout.element.List listNume = new com.itextpdf.layout.element.List();
        com.itextpdf.layout.element.List listSkill = new com.itextpdf.layout.element.List();
        com.itextpdf.layout.element.List listEvaluare = new com.itextpdf.layout.element.List();

        for(UserSkill temp:userSkills){
            listNume.add(temp.getUser().toString());
            listSkill.add(temp.getSkill().toString());
//            listEvaluare.add();
        }
            ImageData data = ImageDataFactory.create(getClass().getClassLoader().getResource("expleoImg.png"));
            Image image = new Image(data);

        try{
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Paragraph paragraph = new Paragraph("View People with skill:"+ text +" and evaluation from:" + evaluation + "\n\n");

            document.add(image);
            document.add(paragraph);
            document.add(listNume);
            document.add(listSkill);

            document.close();
            writer.close();
            pdf.close();

            System.out.println("List added");

//////////////////////////////////////////////////// download form

            response.setContentType("application/pdf");

            response.setHeader("Content-disposition","attachment;filename="+ "testPDF.pdf");

            File file1 = new File("C:/Users/vfbaldovin/Desktop/bla.pdf");

            FileInputStream fileInputStream = new FileInputStream(file1);

            DataOutputStream os = new DataOutputStream(response.getOutputStream());

            response.setHeader("Content-Length",String.valueOf(file1.length()));

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }

            os.flush();
            os.close();
            fileInputStream.close();

        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }


        return "searchPeople";
    }


    @GetMapping("/addNewProject")
    public String addNewProject(Model model){

        Proiect newProject = new Proiect();
        model.addAttribute("newProject", newProject);

        return "leaders_leadersAddNewProject";
    }

    @PostMapping("/addProject")
    public String addProjectToDb(@Valid @ModelAttribute("newProject") Proiect proiect, BindingResult result){

        if(result.hasErrors()){
            return "leaders_leadersAddNewProject";
        }

        UserExpleo user = userService.getUserExpleoPrincipal();
        proiect.setCodProiect(proiect.getCodProiect().toUpperCase());
        proiect.setManager(user.getNumarMatricol());

        proiectService.saveNewProject(proiect);

        return "redirect:/webCM/leaders";
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
