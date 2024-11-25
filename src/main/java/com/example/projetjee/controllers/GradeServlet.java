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

public class GradeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET /grade");
        int teacherId = (int) request.getSession().getAttribute("userId");
        logger.info("teacherId: " + teacherId);
        try {
            List<Course> courses = DbConnect.getCoursesByTeacher(teacherId);
            logger.info("courses: " + courses);

            String coursesJson = new Gson().toJson(courses);
            response.getWriter().write(coursesJson);
        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Erreur lors de la récupération des cours : " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des cours.");
        }
    }
}
