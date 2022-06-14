package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Record;
import com.expleo.webcm.entity.expleodb.Solution;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.helper.RecordSolution;
import com.expleo.webcm.service.ChatbotService;
import com.expleo.webcm.service.RetexService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/retex")
public class RetexController {

    @Autowired
    private RetexService retexService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatbotService chatbotService;

    @GetMapping
    public String indexPage(ModelMap model){

        List<Solution> solutionList = retexService.getLastTenSolutions();
        model.addAttribute("solutionList", solutionList);
        
        return "retexIndex";
    }

    @GetMapping("/addNewRetex")
    public String addNewRetex(Model model){

        Record record = new Record();
        Solution solution = new Solution();

        RecordSolution recordSolution = new RecordSolution(record,solution);

        model.addAttribute("recordSolution", recordSolution);

        return "retexAddNewRetex";
    }

    @PostMapping(value = "/saveNewRetex")
    public String saveNewRetex(@ModelAttribute("recordSolution") RecordSolution recordSolution, BindingResult result){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        UserExpleo userExpleo = userService.getUserExpleoPrincipal();

        Record record = recordSolution.getRecord();

        record.setAutor(userExpleo.getNume() + " " + userExpleo.getPrenume());
        record.setId_autor((long) userExpleo.getId());

        Solution solution = recordSolution.getSolution();

        solution.setUserExpleo(userExpleo);

        String data = dateFormat.format(new Date());
        solution.setDate(data);
        solution.setRecord(record);

        retexService.saveOrUpdateRecord(record);
        retexService.saveOrUpdateSolution(solution);
        return "redirect:/retex";
    }

    @PostMapping(value = "/editRetex")
    public String editRetex(@RequestParam("idSolutie") Integer idSolutie,
                            @RequestParam("textSolutie") String textSolutie,
                            @RequestParam("recordId") Integer recordId){


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String data = dateFormat.format(new Date());

        Solution solution = retexService.getSolution(idSolutie);
        solution.setSolutie(textSolutie);

        solution.setDate_update(data);

        retexService.saveOrUpdateSolution(solution);
        return "redirect:/retex/solution?recordId=" + recordId;
    }

    @GetMapping("/search")
    public String searchResult(@RequestParam("terms") String searchTerms, @RequestParam("category") String searchCategory,
                               ModelMap model){

        List<Record> recordsFound = retexService.searchRecords(searchTerms, searchCategory);
        model.addAttribute("recordsFound", recordsFound);

        return "retexSearchResult";
    }

    @GetMapping("/solution")
    public String showSolution(@RequestParam("recordId") Integer recordId, ModelMap model) {
        List<Solution> solutionList = retexService.getSolutions(recordId);

        UserExpleo mainUser = userService.getUserExpleoPrincipal();
        Record record = retexService.getRecord(recordId);

        model.addAttribute("mainUser", mainUser);
        model.addAttribute("solutionList", solutionList);
        model.addAttribute("record", record);

        return "retexSolutions";
    }

    @PostMapping(value = "/addSolution")
    public String editRetex(@RequestParam("textSolutie") String textSolutie,
                            @RequestParam("recordId") Integer recordId){
        UserExpleo userExpleo = userService.getUserExpleoPrincipal();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String data = dateFormat.format(new Date());

        Solution solution = new Solution();
        solution.setSolutie(textSolutie);
        solution.setDate(data);
        Record record = retexService.getRecord(recordId);
        solution.setRecord(record);
        solution.setUserExpleo(userExpleo);

        retexService.saveOrUpdateSolution(solution);
        return "redirect:/retex/solution?recordId=" + recordId;
    }

    @PostMapping(value = "/reply")
    @ResponseBody
    public String chatBox(@RequestParam("terms") String searchTerms){

        if (searchTerms.isEmpty()) {
              return "";
          }
        String st = searchTerms.replace("?"," ").replace("!"," ").replace("."," ");

        List<Solution> solutionsFound = chatbotService.searchSolutions(st);
        List<String> propozitii = new ArrayList<>();

        for(Solution test : solutionsFound){
            String[] array = test.getSolutie().split("\\.");

            Collections.addAll(propozitii, array);
        }

        StringBuilder chatResponse = new StringBuilder();
        for(Solution solution : solutionsFound)
            chatResponse.append("<a href=\"retex/solution?recordId=").append(solution.getRecord().getId()).append("\">").append(solution.getRecord().getCategorie()).append("</a>, ");

        if(solutionsFound.size()==1){
            return chatResponse.toString().substring(0, chatResponse.length() - 2);}
        else if(solutionsFound.size()>1){

        return "Please choose the appropriate link:<br>" + chatResponse.toString().substring(0, chatResponse.length() - 2);
        }

        return "I'm sorry, I can't help you.";
    }

}
