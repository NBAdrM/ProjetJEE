package com.example.projetjee.models;

import com.example.projetjee.utils.DbConnnect;

import java.sql.SQLException;

public class Course {
    private int id;
    private String name;
    private int degree;
    private int year;
    private int teacherId;
    private String teacherName;

    public Course(int id, String name, int degree ,int year,int teacherId) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.year = year;
        this.teacherId = teacherId;
        this.teacherName = DbConnnect.getTeacher(teacherId).getLastName();
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

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}

