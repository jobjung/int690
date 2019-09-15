package com.company;

import java.util.Vector;

public class Student {
    int id;
    String name;
    double gpa;
    String email;
    Vector<String> advisors;

    public Student(int id, String name, double gpa, String email) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.email = email;
        this.advisors = new Vector<String>();
    }

    public void addAdvisors(String advisorName) {
        this.advisors.add(advisorName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gpa=" + gpa +
                ", email='" + email + '\'' +
                ", advisors=" + advisors.toString() +
                '}';
    }
}
