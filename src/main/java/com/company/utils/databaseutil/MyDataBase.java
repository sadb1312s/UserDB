package com.company.utils.databaseutil;


import com.company.model.User;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

//very simply db, write date to file
public class MyDataBase {
    private static final String attributeDelimiter = "', ";
    private static final String recordDelimiter = "\n";
    private static final String dbName = "db.txt";
    private static final String uploadDir = "upload\\";
    private static final String comment = "//";
    private static final String successMessage = "record added successfully <br />";
    private static final String errorMessage = "such record already exists <br />";
    private static final String invalidRecord = "invalid record  <br />";

    private final String dir;
    private final String path;
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

    //need rewrite
    private void createFile(){
        try {
            dataBase.createNewFile();

            Field[] fields = User.class.getDeclaredFields();

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

    public void writeRecord(User user){
        addResult = new StringBuilder();
        if(user.validate()) {
            write(user);
        }else {
            addResult.append(user).append(" : ").append(user.getCheckResult());
        }
    }

    public void writeRecord(MultipartFile file, User user){
        addResult = new StringBuilder();
        //check empty and type of file
        if(!fileCheck(file)){
            user.setCheckResult("invalid file");
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
            Files.delete(path);
        } catch (IOException e) {
            addResult.append(e.getMessage());
            e.printStackTrace();
        }

        user.setCheckResult(addResult.toString());
    }

    private void write(String string){
        try (FileWriter writer = new FileWriter(dataBase,true)){
            writer.write(string);
            writer.write(recordDelimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean write(User user){
        boolean add = false;

        try (FileWriter writer = new FileWriter(dataBase,true)){
            String record = user.toString();

            if(equalsRecordCheck(record)){
                writer.write(record);
                writer.write(recordDelimiter);

                addResult.append(user).append(" : ").append(successMessage);
                add = true;
            }else {
                addResult.append(user).append(" : ").append(errorMessage);
            }

        } catch (IOException e) {
            addResult.append(e.getMessage()).append("<br />");
            e.printStackTrace();
        }

        user.setCheckResult(addResult.toString());

        return add;
    }

    private boolean fileCheck(MultipartFile  file){

        if(file.isEmpty()){
            return false;
        }

        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);

        return type.equals("txt");
    }

    //read uploaded file and check records
    private void parseUploadFile(File file){

        try (Scanner s = new Scanner(file)){


            List<String> attributes;
            List<User> goodData = new ArrayList<>();

            int addCount = 0;
            int fileRecCount = 0;

            while (s.hasNextLine()){

                String str = s.nextLine();

                if(str.length() != 0 && !str.substring(0,1).contains(comment)) {
                    fileRecCount++;
                    attributes = new ArrayList<>(Arrays.asList(str.split(", ")));

                    if (attributes.size() == User.attributeCount) {
                        attributes = attributes.stream().map(rec->rec.substring(rec.indexOf("'") + 1,rec.lastIndexOf("'"))).collect(Collectors.toList());
                        User g = new User(attributes);

                        if (g.validate()) {
                            goodData.add(g);
                        }else {
                            addResult.append(str).append(" : ").append(invalidRecord);
                        }

                    }else {
                        addResult.append(str).append(" : ").append(invalidRecord);
                    }
                }

            }

            for(User g : goodData){
                if(write(g)){
                    addCount++;
                }
            }
            addResult.append("add ").append(addCount).append(" record from ").append(fileRecCount);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean equalsRecordCheck(String record){
        Scanner scanner = null;
        try {
            scanner = new Scanner(dataBase);

            while (scanner.hasNextLine()){

                if(scanner.nextLine().equals(record)){
                    return false;
                }
            }

        } catch (FileNotFoundException e) {
            addResult.append(e.getMessage()).append("<br />");
            e.printStackTrace();
        }

        return true;
    }

    public String search(String value, String fieldName){

        List<String> attributes;
        StringBuilder found = new StringBuilder();
        String desired = fieldName+"='"+value+"'";

        try(Scanner s = new Scanner(dataBase)){
            while (s.hasNextLine()) {
                String str = s.nextLine();
                attributes = new ArrayList<>(Arrays.asList(str.split(", ")));

                String recVal = attributes.stream().filter(s1 -> s1.substring(0,s1.indexOf("=")).equals(fieldName)).collect(Collectors.toList()).get(0);

                if(recVal.equals(desired)){
                    found.append(str).append("<br />");
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String result = found.toString();
        System.out.println(result.equals(""));
        return result;
    }
}
