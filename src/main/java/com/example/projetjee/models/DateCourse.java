package com.example.projetjee.models;

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
}