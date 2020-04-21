package com.company.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.company.databaseutil.*;

public class Greeting {
    public static final int attributeCount = 7;

    private String surname;
    private String firstname;
    private String patronymic;
    private int age;
    private int salary;
    private String email;
    private String workPlace;

    private String checkResult;

    public Greeting(){

    }

    public Greeting(List<String> attributes) {
        surname = attributes.get(0);
        firstname = attributes.get(1);
        patronymic = attributes.get(2);

        try {
            age = Integer.parseInt(attributes.get(3));
            salary = Integer.parseInt(attributes.get(4));
        }catch (NumberFormatException e){
            checkResult += e.getMessage() + " ";
            e.printStackTrace();
        }

        System.out.println("!!!");

        email = attributes.get(5);
        workPlace = attributes.get(6);
    }

    public boolean validate(){
        List<String> list = new ArrayList<>();

        if(surname.equals("")){
            list.add("surname can't be null");
        }

        if(firstname.equals("")){
            list.add("firstname can't be null");
        }

        if(age <= 0){
            list.add("age can't be <= 0");
        }
        if(salary <= 0){
            list.add("salary can't be <= 0");
        }

        if(email.equals("")||!email.contains("@")){
            list.add("email is't valid");
        }

        if(workPlace.equals("")){
            list.add("workPlace can't be null");
        }

        if(list.size() != 0) {
            checkResult = this +" : "+list.stream().collect(Collectors.joining(", "));
            return false;
        } else {
            return true;
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", workPlace='" + workPlace + '\'' +
                '}';
    }
}
