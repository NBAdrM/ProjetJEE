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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class GradeCourseListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            logger.info("Course selected, student selected");
            int studentId = (int) request.getSession().getAttribute("userId");
            int courseId = parseInt(request.getParameter("courseId"));
            logger.info("studentId : " + studentId + "& courseId : " + courseId);

            request.getSession().setAttribute("courseId", courseId);

            try {
                List<Grade> grades = DbConnect.getGrades(studentId, courseId);
                logger.info("grades :" + grades);

                request.getSession().setAttribute("grades", grades);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            request.getRequestDispatcher("grade/gradeList.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET /gradeCourseList");
        int studentId = (int) request.getSession().getAttribute("userId");
        logger.info("studentId: " + studentId);
        try {
            List<Course> courses = DbConnect.getCoursesByStudent(studentId);
            logger.info("courses: " + courses);

            String coursesJson = new Gson().toJson(courses);
            response.getWriter().write(coursesJson);

        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Erreur lors de la récupération des cours : " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des cours.");
        }
    }
}
