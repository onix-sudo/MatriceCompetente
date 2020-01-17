package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Record;
import com.expleo.webcm.entity.expleodb.Solution;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.helper.RecordSolution;
import com.expleo.webcm.service.RetexService;
import com.expleo.webcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/retex")
public class RetexController {

    @Autowired
    private RetexService retexService;

    @Autowired
    private UserService userService;


    @GetMapping
    public String indexPage(ModelMap model){
        List<Record> recordList = retexService.getLastTenRecords();
        model.addAttribute("recordList", recordList);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String data = dateFormat.format(Calendar.getInstance().getTime());

        UserExpleo userExpleo = userService.getUserExpleoPrincipal();

        Record record = recordSolution.getRecord();

        record.setAutor(userExpleo.getNume() + " " + userExpleo.getPrenume());
        record.setId_autor((long) userExpleo.getId());

        Solution solution = recordSolution.getSolution();

        solution.setAutor(userExpleo.getNume() + " " + userExpleo.getPrenume());
        solution.setDate(data);
        solution.setRecord(record);

        retexService.saveOrUpdateRecord(record);
        retexService.saveOrUpdateSolution(solution);
        return "redirect:/retex";
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

        model.addAttribute("solutionList", solutionList);

        Record record = retexService.getRecord(recordId);

        model.addAttribute("record", record);

        return "retexSolutions";
    }
}
