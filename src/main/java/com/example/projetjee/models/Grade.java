package com.example.projetjee.models;

public class Grade {
    private String studentId;
    private String courseId;
    private String courseName;
    private double grade;

    // Constructeurs, getters et setters
    public Grade(String studentId, String courseId, String courseName, double grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.grade = grade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}

