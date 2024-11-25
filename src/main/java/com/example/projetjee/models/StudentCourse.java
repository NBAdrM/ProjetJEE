package com.example.projetjee.models;

import java.util.List;

public class StudentCourse {
    private int courseId;
    private int studentId;
    private List<Grade> grades;
    private Integer degree;

    public StudentCourse(int courseId, int studentId, List<Grade> grades) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.grades = grades;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }
}