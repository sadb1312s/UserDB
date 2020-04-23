package com.company;

import com.company.utils.databaseutil.MyDataBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringBootStarter {
    public static MyDataBase dataBase;

    public static void main(String[] args) {

        dataBase = new MyDataBase();
        SpringApplication.run(SpringBootStarter.class, args);
    }
}
