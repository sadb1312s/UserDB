package com.company.service;

import com.company.model.Search;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.company.SpringBootStarter.dataBase;

@Service
public class SearchService {

    public void search(Search search, String userAgent){
        Date date = new Date();

        String result;
        String whatSearch;

        if(search.getForName() == null){
            whatSearch = "surname";
        }else {
            whatSearch = "firstname";
        }

        result = dataBase.search(search.getValue(),whatSearch);

        if("".equals(result)){
            result = "no found";
        }else {
            result += " user-agent: " + userAgent + "<br />request time: "+date;
        }

        search.setSearchResult(result);
    }
}
