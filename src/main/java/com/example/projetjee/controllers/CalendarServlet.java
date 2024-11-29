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
            List<DateCourse> dateCourse = null;
            if (role.equals("student")){
                List<StudentCourse> studentCourses = DbConnect.getStudentCourses(userId);
                logger.info("studentCourses: " + studentCourses);
                for(StudentCourse studentCourse : studentCourses) {
                    dateCourse = DbConnect.getDateCourse(studentCourse.getCourseId());
                    logger.info("dateCourse: " + dateCourse);
                }
            } else if (role.equals("teacher")) {
                List<Course> teacherCourses = DbConnect.getCoursesByTeacher(userId);
                logger.info("teacherCourses: " + teacherCourses);
                for(Course teacherCourse : teacherCourses) {
                    dateCourse = DbConnect.getDateCourse(teacherCourse.getId());
                    logger.info("dateCourse: " + dateCourse);
                }
            } else {
                logger.severe("Erreur lors de la récupération des cours : role inconnu");
                }

            request.setAttribute("dateCourse", dateCourse);
            request.getRequestDispatcher("/course/courseCalendar.jsp").forward(request, response);
        } catch (Exception e) {
            logger.severe("Erreur lors de la récupération des cours : " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des cours.");
        }
    }
}
