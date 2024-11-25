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
}

