package com.expleo.webcm.controllers;

import com.expleo.webcm.entity.expleodb.Record;
import com.expleo.webcm.service.RetexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/retex")
public class RetexController {

    @Autowired
    private RetexService retexService;

    @GetMapping
    public String indexPage(){
        return "retexIndex";
    }

    @GetMapping("/addNewRetex")
    public String addNewRetex(){
        return "retexAddNewRetex";
    }

    @PostMapping(value = "/saveNewRetex")
    public String saveNewRetex(){
        return "redirect:/retex";
    }

    @GetMapping("/search")
    public String searchResult(@RequestParam("terms") String searchTerms, @RequestParam("category") String searchCategory,
                               ModelMap model){

        List<Record> recordsFound = retexService.searchRecords(searchTerms, searchCategory);
        model.addAttribute("recordsFound", recordsFound);

        return "retexSearchResult";
    }
}
