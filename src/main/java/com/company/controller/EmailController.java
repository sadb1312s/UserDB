package com.company.controller;

import com.company.model.Email;
import com.company.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {

    @Autowired
    JavaMailSender emailSender;
    @Autowired
    EmailService emailService;

    @GetMapping("/sendForm")
    public String sendForm(Model model){
        model.addAttribute("sendForm", new Email());
        return "sendForm";
    }

    @PostMapping("/sendForm")
    public String sendForm(@ModelAttribute Email email){
        System.out.println(email);

        emailService.sendEmail(emailSender,email);

        return "sendResult";
    }
}
