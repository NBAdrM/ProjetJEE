package com.example.projetjee.models;

public class Grade {

    private double grade;
    private String date;

    public Grade( double grade,String date) {
        this.grade = grade;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}

