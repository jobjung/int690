package com.company;

import java.util.Vector;

public class Student {
    int id;
    String firstName;
    String lastName;
    double gpa;
    String email;
    Vector<String> advisors;

    public Student(int id, String firstName, String lastName, double gpa, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
        this.email = email;
        this.advisors = new Vector<String>(5);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Vector<String> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(Vector<String> advisors) {
        this.advisors = advisors;
    }

    public void addAdvisors(String advisorName) {
        this.advisors.add(advisorName);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gpa=" + gpa +
                ", email='" + email + '\'' +
                ", advisors=" + advisors +
                '}';
    }
}
