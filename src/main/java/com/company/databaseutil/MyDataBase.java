package com.company.databaseutil;


import com.company.model.Greeting;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

//very simply db, write date to file
public class MyDataBase {
    private static final String attributeDelimiter = " ";
    private static final String recordDelimiter = "\n";
    private static final String dbName = "db.txt";
    private static final String uploadDir = "upload\\";
    private static final String comment = "//";
    private static final String successMessage = "record added successfully <br />";
    private static final String errorMessage = "such record already exists <br />";
    private static final String invalidRecord = "invalid record  <br />";

    private String dir;
    private String path;
    private File dataBase;
    private StringBuilder addResult;

    public MyDataBase() {
        addResult = new StringBuilder();
        dir = System.getProperty("user.dir") +"\\";
        path = dir + dbName;
        createDB();
    }

    private void createDB(){
        dataBase = new File(this.path);

        if(!dataBase.exists()){
            createFile();
        }
    }

    private void createFile(){
        try {
            dataBase.createNewFile();

            Field[] fields = Greeting.class.getDeclaredFields();

            write(comment + "record format : field 1 + attributeDelimiter + field2 ... + fieldN + recordDelimiter");

            int i = 1;
            for(Field field : fields){
                if(!field.getName().equals("attributeCount") && !field.getName().equals("checkResult") ) {
                    write(comment + "field "+ i + " : " + field.getName() + " type= " + field.getType().getSimpleName());
                    i++;
                }
            }

            write(comment + "Attribute delimiter = '" + attributeDelimiter + "'");
            write(comment + "Record delimiter = '\\n'");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRecord(Greeting greeting){
        addResult = new StringBuilder();
        write(greeting);
    }

    public void writeRecord(MultipartFile file,Greeting greeting){
        addResult = new StringBuilder();
        //check empty and type of file
        if(!fileCheck(file)){
            greeting.setCheckResult("invalid file");
            return;
        }


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        File uploadPath = new File(dir + uploadDir);
        if(!uploadPath.isDirectory()){
            uploadPath.mkdir();
        }

        try {
            Path path = Paths.get(dir + uploadDir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            parseUploadFile(path.toFile());
        } catch (IOException e) {
            addResult.append(e.getMessage());
            e.printStackTrace();
        }

        greeting.setCheckResult(addResult.toString());
    }

    private void write(String string){
        try (FileWriter writer = new FileWriter(dataBase,true)){
            writer.write(string);
            writer.write(recordDelimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean write(Greeting greeting){
        boolean add = false;
        //serializable or externalize maybe

        try (FileWriter writer = new FileWriter(dataBase,true)){
            String record = formRecord(greeting);

            if(equalsRecordCheck(record)){
                writer.write(record);
                writer.write(recordDelimiter);

                addResult.append(greeting +" : " + successMessage);
                add = true;
            }else {
                addResult.append(greeting + " : " + errorMessage);
            }

        } catch (FileNotFoundException e) {
            addResult.append(e.getMessage() + "<br />");
            e.printStackTrace();
        } catch (IOException e) {
            addResult.append(e.getMessage() + "<br />");
            e.printStackTrace();
        }



        greeting.setCheckResult(addResult.toString());

        return add;
    }

    private boolean fileCheck(MultipartFile  file){

        if(file.isEmpty()){
            return false;
        }

        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);

        if(!type.equals("txt")){
            return false;
        }

        return true;
    }

    //read uploaded file and check records
    private void parseUploadFile(File file){

        try {
            Scanner s = new Scanner(file);

            List<String> attributes;
            List<Greeting> goodData = new ArrayList<>();

            int addCount = 0;

            while (s.hasNextLine()){

                String str = s.nextLine();

                if(!str.substring(0,1).contains(comment)) {
                    attributes = new ArrayList<>(Arrays.asList(str.split(" ")));

                    if (attributes.size() == Greeting.attributeCount) {
                        Greeting g = new Greeting(attributes);

                        if (g.validate()) {
                            goodData.add(g);
                            addCount++;
                        }else {
                            addResult.append(str + " : "+invalidRecord);
                        }

                    }else {
                        addResult.append(str + " : "+invalidRecord);
                    }
                }

            }

            goodData.forEach(this::write);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String formRecord(Greeting greeting){
        StringBuilder builder = new StringBuilder();
        builder.append(greeting.getSurname() + attributeDelimiter);
        builder.append(greeting.getFirstname() + attributeDelimiter);
        builder.append(greeting.getPatronymic() + attributeDelimiter);
        builder.append(greeting.getAge() + attributeDelimiter);
        builder.append(greeting.getSalary() + attributeDelimiter);
        builder.append(greeting.getEmail() + attributeDelimiter);
        builder.append(greeting.getWorkPlace());

        return builder.toString();
    }

    private boolean equalsRecordCheck(String record) throws IOException{
        Scanner scanner = new Scanner(dataBase);

        while (scanner.hasNextLine()){

            if(scanner.nextLine().equals(record)){
                return false;
            }
        }

        return true;
    }
}
