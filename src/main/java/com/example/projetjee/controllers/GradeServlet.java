package com.example.projetjee.controllers;

import com.example.projetjee.models.Course;
import com.example.projetjee.models.Grade;
import com.example.projetjee.models.Student;
import com.example.projetjee.utils.DbConnect;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class GradeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Course selected, teacher selected");
        int teacherId = (int) request.getSession().getAttribute("userId");
        int courseId = parseInt(request.getParameter("courseId"));
        logger.info("teacherId : " + teacherId + "& courseId : " + courseId);

        request.getSession().setAttribute("courseId", courseId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET /grade");
        String type = request.getParameter("type");
        int teacherId = (int) request.getSession().getAttribute("userId");
        int courseId = (int) request.getSession().getAttribute("courseId");
        logger.info("teacherId: " + teacherId);
        try {
            if ("students".equalsIgnoreCase(type)) {
                List<Student> students = DbConnect.getStudentsByCourse(courseId);
                logger.info("students: " + students);

                String studentsJson = new Gson().toJson(students);
                response.getWriter().write(studentsJson);
            }

            else {
                List<Course> courses = DbConnect.getCoursesByTeacher(teacherId);
                logger.info("courses: " + courses);

                String coursesJson = new Gson().toJson(courses);
                response.getWriter().write(coursesJson);
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Erreur lors de la récupération des cours : " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des cours.");
        }
    }
}
