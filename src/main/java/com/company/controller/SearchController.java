package com.company.controller;

import com.company.model.Greeting;
import com.company.model.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.company.SpringBootStarter.dataBase;

@Controller
public class SearchController {

    @GetMapping("/findForm")
    public String addForm(Model model){
        model.addAttribute("findForm", new Search());
        return "findForm";
    }

    @PostMapping("/findForm")
    public String searchSubmit(@ModelAttribute Search search){


        String result;
        if(search.getForName()!=null){
            result = dataBase.search(search.getValue(),"firstname");
        }else {
            result =  dataBase.search(search.getValue(),"surname");
        }


        search.setSearchResult(result);


        return "searchResult";
    }
}
