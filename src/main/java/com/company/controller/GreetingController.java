package com.company.controller;

import com.company.databaseutil.MyDataBase;
import com.company.model.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.company.SpringBootStarter.dataBase;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String addForm(Model model){
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String addFormSubmit(@ModelAttribute Greeting user){

        dataBase.writeRecord(user);

        return "result";
    }
}
