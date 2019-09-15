package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class lab71 {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Student st1 = new Student(11, "Tom", 3.2, "tom@mail.com");
        st1.addAdvisors("Tony");
        st1.addAdvisors("Alice");
        st1.addAdvisors("James");
        System.out.println(st1);
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream("student.json"), "UTF-8");
            gson.toJson(st1, writer);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Student st2 = new Student(22, "Ann", 3.2, "Ann@mail.com");
        st2.addAdvisors("Tony");
        st2.addAdvisors("Steve");
        st2.addAdvisors("Peter");
        Course course1 = new Course("INT690", "Data Science", 3);
        Course course2 = new Course("INT601", "ECP", 6);
        Course course3 = new Course("INT602", "Algorithms", 1);
        Registers registers =new Registers();
        registers.addRegisters(new Register(st1,course2,"B+"));
        registers.addRegisters(new Register(st1,course3,"C"));
        registers.addRegisters(new Register(st2,course1,"A"));
        registers.addRegisters(new Register(st2,course2,"C+"));
        registers.addRegisters(new Register(st2,course3,"D"));
        System.out.println(registers);

        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream("student2.json"), "UTF-8");
            gson.toJson(registers, writer);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
