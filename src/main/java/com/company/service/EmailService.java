package com.company.service;

import com.company.model.Email;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(JavaMailSender emailSender, Email email) {
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
    }
}
