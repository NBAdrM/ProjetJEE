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

public class GradeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sourcePage = (String) request.getSession().getAttribute("sourcePage");
        if (sourcePage == "gradeEntry.jsp"){
            logger.info("Course selected, teacher selected");
            int teacherId = (int) request.getSession().getAttribute("userId");
            int courseId = parseInt(request.getParameter("courseId"));
            logger.info("teacherId : " + teacherId + "& courseId : " + courseId);

            request.getSession().setAttribute("courseId", courseId);

            try {
                List<Student> students = DbConnect.getStudentsByCourse(courseId);
                logger.info("students :" + students);

                request.getSession().setAttribute("students", students);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            request.getRequestDispatcher("grade/gradeEntry2.jsp").forward(request, response);
        }

        if (sourcePage == "gradeEntry2.jsp"){
            logger.info("Course selected, Student selected");
            int courseId = (int) request.getSession().getAttribute("courseId");
            int studentId = parseInt(request.getParameter("studentId"));
            int grade = parseInt(request.getParameter("grade"));
            logger.info("studentId : " + studentId + "& courseId : " + courseId);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = Calendar.getInstance().getTime();
            String date = df.format(today);

            try {
                DbConnect.addGrade(studentId,courseId,grade,date);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            request.getRequestDispatcher("grade/gradeEntry.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET /grade");
        int teacherId = (int) request.getSession().getAttribute("userId");
        String sourcePage = (String) request.getSession().getAttribute("sourcePage");
        logger.info("teacherId: " + teacherId);
        logger.info("sourcePage: " + sourcePage);
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
