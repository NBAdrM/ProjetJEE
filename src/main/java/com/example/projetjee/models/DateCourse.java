package com.example.projetjee.models;

import java.sql.Date;

public class DateCourse {
    private int id;
    private int courseId;
    private java.sql.Date date;
    private String classroom;
    private String startTime;
    private String endTime;

    public DateCourse(int id, int courseId, java.sql.Date date,String classroom, String startTime, String endTime) {
        this.id = id;
        this.courseId = courseId;
        this.date = date;
        this.classroom = classroom;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}