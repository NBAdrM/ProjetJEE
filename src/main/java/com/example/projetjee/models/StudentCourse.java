package com.example.projetjee.models;

public class StudentCourse {
    private int courseId;
    private int studentId;
    private Integer grade;
    private Integer degree;

    public StudentCourse(int courseId, int studentId, Integer grade, Integer degree) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.degree = degree;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }
}