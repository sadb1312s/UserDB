package com.company.controller;

import com.company.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.company.SpringBootStarter.dataBase;

@Controller
public class UploadController {


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, User user) {


        dataBase.writeRecord(file,user);

        return "result";
    }
}
