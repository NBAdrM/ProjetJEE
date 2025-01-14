package com.example.projetjee.controllers;

import com.example.projetjee.models.*;
import com.example.projetjee.utils.DbConnect;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CalendarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AuthServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET /calendar");
        int userId = (int) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");
        logger.info("userId: " + userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<DateCourseDetail> detailedCourses = new ArrayList<>();
            if (role.equals("student")){
                List<StudentCourse> studentCourses = DbConnect.getStudentCourses(userId);
                logger.info("studentCourses: " + studentCourses);
                for(StudentCourse studentCourse : studentCourses) {
                    List<DateCourse> newDateCourses = DbConnect.getDateCourse(studentCourse.getCourseId());
                    Course course = DbConnect.getCourseById(studentCourse.getCourseId());
                    String courseName = course.getName();
                    String courseTeacher = course.getTeacherName();
                    logger.info("dateCourse: " + newDateCourses);
                    if (newDateCourses != null && !newDateCourses.isEmpty()) {
                        for (DateCourse dateCourse : newDateCourses) {
                            detailedCourses.add(new DateCourseDetail(dateCourse, courseName, courseTeacher));
                        }
                    } else {
                        logger.info("Aucun cours trouvé pour l'étudiant");
                    }
                }
            } else if (role.equals("teacher")) {
                List<Course> teacherCourses = DbConnect.getCoursesByTeacher(userId);
                logger.info("teacherCourses: " + teacherCourses);
                for(Course teacherCourse : teacherCourses) {
                    List<DateCourse> newDateCourses = DbConnect.getDateCourse(teacherCourse.getId());
                    String courseName = teacherCourse.getName();
                    String courseTeacher = teacherCourse.getTeacherName();
                    logger.info("dateCourse: " + newDateCourses);

                    if (newDateCourses != null && !newDateCourses.isEmpty()) {
                        for (DateCourse dateCourse : newDateCourses) {
                            detailedCourses.add(new DateCourseDetail(dateCourse, courseName, courseTeacher));
                        }
                    } else {
                        logger.info("Aucun cours trouvé pour l'enseignant");
                    }
                }
            } else {
                logger.severe("Erreur lors de la récupération des cours : role inconnu");
                }

            logger.info("detailedCourses: " + detailedCourses);
            request.setAttribute("detailedCourse", detailedCourses);
            request.getRequestDispatcher("/course/courseCalendar.jsp").forward(request, response);
        } catch (Exception e) {
            logger.severe("Erreur lors de la récupération des cours : " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des cours.");
        }
    }
}
