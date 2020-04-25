package com.company.controller;

import com.company.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.company.SpringBootStarter.dataBase;

@Controller
public class UserController {

    @GetMapping("/addForm")
    public String addForm(Model model){
        model.addAttribute("addForm", new User());
        return "addForm";
    }

    @PostMapping("/addForm")
    public String addFormSubmit(@ModelAttribute User user){

        System.out.println(dataBase);
        dataBase.writeRecord(user);

        return "result";
    }
}
