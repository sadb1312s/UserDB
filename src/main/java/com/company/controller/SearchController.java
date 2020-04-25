package com.company.controller;

import com.company.model.Search;
import com.company.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/findForm")
    public String addForm(Model model){
        model.addAttribute("findForm", new Search());
        return "findForm";
    }

    @PostMapping("/findForm")
    public String searchSubmit(@ModelAttribute Search search, HttpServletRequest request){

        searchService.search(search, request.getHeader("User-Agent"));

        return "searchResult";
    }
}
