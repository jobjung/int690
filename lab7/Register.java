package com.company;

public class Register {
    Student student;
    Course course;
    String grade;

    public Register(Student student, Course course, String grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Register{" +
                "student=" + student +
                ", course=" + course +
                ", grade='" + grade + '\'' +
                '}';
    }
}
