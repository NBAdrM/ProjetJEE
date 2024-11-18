package com.example.projetjee.models;

public class Student extends Person {
    private String report;

    public Student(int id, String firstName, String lastName, String email, String address, String username, String password, boolean active, String report) {
        super(id, firstName, lastName, email, address, username, password, active);
        this.report = report;
    }

    public String getReport() {
        return report;
    }
    public void setReport(String report) {
        this.report = report;
    }
}
