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
     * The method creates the model for the radar chart for the selected project
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
     * The method creates the model for the personal profile of the user and returns the view for it
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
     * The method creates the model for the add skill page and returns the view for it
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
     * The method searches for the skills based on the searchTerm,
     * adding the result in the model and returning the view
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
     * The method modifies the evaluations for the choosen project's skills and reloads the radar chart
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
     * The method displays all of the projects that the logged-in user is assigned to
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
     * The method extracts the evaluation history for all of the logged-in user's skills
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
