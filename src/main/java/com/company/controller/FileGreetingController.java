package com.company.controller;

import com.company.databaseutil.MyDataBase;
import com.company.model.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.company.SpringBootStarter.dataBase;

@Controller
public class FileGreetingController {


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Greeting g) {


        dataBase.writeRecord(file,g);

        return "result";
    }
}
