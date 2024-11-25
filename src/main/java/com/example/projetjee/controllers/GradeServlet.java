package com.example.projetjee.controllers;

import com.example.projetjee.models.Grade;
import com.example.projetjee.models.Student;
import com.example.projetjee.utils.DbConnnect;
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
        String studentFirstName = request.getParameter("studentFirstName");
        String studentLastName = request.getParameter("studentLastName");
        logger.info("request get \nstudentFirstName: " + studentFirstName + "\nstudentLastName: " + studentLastName);

        try {
            List<Student> students = DbConnnect.getStudentsByName(studentLastName, studentFirstName);
            logger.info("students: " + students);

            request.setAttribute("students", students);

            logger.info("forwarding to gradeEntry.jsp");
            request.getRequestDispatcher("/grade/gradeEntry.jsp").forward(request, response);
            ;

        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Error fetching data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

