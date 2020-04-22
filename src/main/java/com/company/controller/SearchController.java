package com.company.controller;

import com.company.model.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.company.SpringBootStarter.dataBase;

@Controller
public class SearchController {

    @GetMapping("/findForm")
    public String addForm(Model model){
        model.addAttribute("findForm", new Search());
        return "findForm";
    }

    @PostMapping("/findForm")
    public String searchSubmit(@ModelAttribute Search search, HttpServletRequest request){


        Date date = new Date();
        System.out.println("---> " + date);

        String result;
        String whatSearch;

        if(search.getForName() == null){
            whatSearch = "surname";
        }else {
            whatSearch = "firstname";
        }

        result = dataBase.search(search.getValue(),whatSearch);

        if(result.equals("")){
            result = "no found";
        }else {
            String userAgent = request.getHeader("User-Agent");
            result += " user-agent: " + userAgent + "<br />request time:"+date;
        }


        search.setSearchResult(result);
        return "searchResult";
    }
}
