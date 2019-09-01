package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class lab6 {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson("Hello World", System.out);
        System.out.println();

        Map<String, String> myMap = new LinkedHashMap<String, String>(5);
        myMap.put("Name", "Tom");
        myMap.put("Address", "New York");
        String json1 = gson.toJson(myMap);
        System.out.println(json1);
        try {
            Student st1 = new Student(666, "Tony", "Smith", 3.42, "tony@a.com");
            st1.addAdvisors("Borworn");
            st1.addAdvisors("Nipon");
            st1.addAdvisors("Olarn");
            FileOutputStream file = new FileOutputStream("student1.json");
            Writer writer = new OutputStreamWriter(file, "UTF-8");
            gson.toJson(st1, writer);
            writer.close();
            file.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Reader reader = new FileReader("student5.json");
            Student st5 = gson.fromJson(reader, Student.class);
            System.out.println(st5);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
