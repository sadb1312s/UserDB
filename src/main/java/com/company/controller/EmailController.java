package com.company.controller;

import com.company.model.Email;
import com.company.utils.emailutils.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
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

    @GetMapping("/sendForm")
    public String sendForm(Model model){
        model.addAttribute("sendForm", new Email());
        return "sendForm";
    }

    @PostMapping("/sendForm")
    public String sendForm(@ModelAttribute Email email){
        System.out.println(email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getEmail());
        message.setSubject("Spring boot email");
        message.setText(email.getText());

        try {
            emailSender.send(message);
        }catch (MailException e){
            email.setSendResult(e.getMessage());
            e.printStackTrace();
        }

        return "sendResult";
    }
}
