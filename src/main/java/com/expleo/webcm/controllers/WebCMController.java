package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.*;
import com.expleo.webcm.helper.HistoryCluster;
import com.expleo.webcm.helper.HistoryForWeb;
import com.expleo.webcm.helper.HistoryForWebUtil;
import com.expleo.webcm.service.*;
import com.expleo.webcm.service.ProiectService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/webCM")
public class WebCMController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProiectService proiectService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public String webCM(ModelMap model){
        return "webCM";
    }

    /**
     * Creeaza modelul pentru matricea de competente pentru proiectul selectat
     * @param model
     * @param projectId
     * @return
     */
    @GetMapping(value = "/cmptMat")
    public String competencyMatrix(ModelMap model, @RequestParam(name = "proiectId") Integer projectId) {

        List<UserSkill> userSkillsFromProject = userSkillService.getUserSkillByProjectSkills(projectId);
        model.addAttribute("userSkillList", userSkillsFromProject);
        model.addAttribute("projectId", projectId);

        return "cmptMat";
    }

    /**
     * Creeaza modelul pentru profilul personal al utilizatorului si returneaza view-ul pentru acesta
     * @param model
     * @return
     */
    @GetMapping("/personalProfile")
    public String personalProfile(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();
        List<UserSkill> userAdditionalSkills = new LinkedList<>();
        List<UserSkill> projectSkills = new LinkedList<>();

        userSkillService.getAdditionalAndProjectSkill(user.getId(), userAdditionalSkills, projectSkills);

        model.addAttribute("projectSkills", projectSkills);
        model.addAttribute("userSkills", userAdditionalSkills);
        model.addAttribute("user", user);

        return "personalProfile";
    }

    /**
     * Creeaza modelul pentru pagina de adaugare a unui skill si returneaza view-ul pentru aceasta
     * @param model
     * @return
     */
    @GetMapping(value = "/personalProfile/showFormForAddSkill")
    public String showFormForAddSkill(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();
        Skill theSkill = new Skill();

        model.addAttribute("skill", theSkill);
        model.addAttribute("user", user);

        return "skill-form";
    }

    /**
     * Cauta skill-urile care se potrivesc searchTerm-ului, adauga in model rezultatul si returneaza view-ul
     * @param text
     * @param theModel
     * @return
     */
    @GetMapping("/personalProfile/showFormForAddSkill/search")
    public String searchSkills(@RequestParam(value = "searchTerm") String text, Model theModel){

        UserExpleo user = userService.getUserExpleoPrincipal();
        List<Skill> searchResult = searchService.searchPrincipalSkill(text, user.getId());

        theModel.addAttribute("result", searchResult);
        theModel.addAttribute("user", user);

        return "skill-form";
    }

    @GetMapping("/personalProfile/showFormForAddSkill/search/addSkillToUser")
    public void addSkilltoUser(@RequestParam(value = "skillId") int skillId,
                               @RequestParam(value = "userID") int userId){
        userSkillService.saveUserSkill(userId, skillId);

    }

    @GetMapping("/deleteSkill")
    @ResponseBody
    public void deleteSkill(@RequestParam("skillId") int idSkill){
        UserExpleo user = userService.getUserExpleoPrincipal();
        userSkillService.removeUserSkill(user.getId(), idSkill);

    }

    @GetMapping("/modifyP")
    public void modify(@RequestParam("evaluation") int eval, @RequestParam("idskill") int theId){
        UserExpleo user = userService.getUserExpleoPrincipal();
        userSkillService.updateUserSkill(user.getId(), theId, eval);

    }

    /**
     * Modifica evaluarile pentru skill-urile proiectului ales si reincarca matricea de competente
     * @param model
     * @param eval
     * @param idskill
     * @param idproiect
     * @return
     */
    @GetMapping(value = "/cmptMat/modifyT")
    public String modifyT(ModelMap model, @RequestParam("evaluation") int eval, @RequestParam("idskill") int idskill,
                          @RequestParam(value = "proiectId") int idproiect){

        UserExpleo user = userService.getUserExpleoPrincipal();
        userSkillService.updateUserSkill(user.getId(), idskill, eval);

        return competencyMatrix(model, idproiect);
    }

    /**
     * Afiseaza proiectele la care utilizatorul logat este asignat
     * @param model
     * @return
     */
    @GetMapping("/currentProj")
    public String currentProjects(ModelMap model) {
        List<Proiect> proiectList = proiectService.findPrincipalProjects();
        model.addAttribute("proiectList", proiectList);

        return "currentProj";
    }

    /**
     * Extrage istoricul evaluarilor pentru toate skill-urile user-ului logat
     * @param model
     * @return
     */
    @RequestMapping("/personalProfile/viewHistory")
    public String viewHistory(ModelMap model){

        UserExpleo user = userService.getUserExpleoPrincipal();

        List<History> histories = historyService.getHistoryByUserId(user.getId());
        List<HistoryForWeb> resultList = new HistoryForWebUtil().makeList(histories);

        model.addAttribute("histories", resultList);

        model.addAttribute("histories", resultList);

        return "personalHistory";
    }

}
