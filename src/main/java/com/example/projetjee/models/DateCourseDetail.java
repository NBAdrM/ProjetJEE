package com.example.projetjee.models;

public class DateCourseDetail {
    private DateCourse dateCourse;
    private String courseName;
    private String teacherName;

    public DateCourseDetail(DateCourse dateCourse, String courseName, String teacherName) {
        this.dateCourse = dateCourse;
        this.courseName = courseName;
        this.teacherName = teacherName;
    }

    public DateCourse getDateCourse() {
        return dateCourse;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
