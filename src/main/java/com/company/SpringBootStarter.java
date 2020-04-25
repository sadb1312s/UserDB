package com.company;

import com.company.utils.databaseutil.MyDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringBootStarter {

    @Autowired
    public static MyDataBase dataBase;

    public static void main(String[] args) {
        dataBase = new MyDataBase();
        SpringApplication.run(SpringBootStarter.class, args);
    }
}
