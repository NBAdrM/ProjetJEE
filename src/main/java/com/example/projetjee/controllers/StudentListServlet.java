
package com.example.projetjee.controllers;

import com.example.projetjee.models.Student;
import com.example.projetjee.utils.DbConnect;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class StudentListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        logger.info("POST /StudentList");

        int studentId = Integer.parseInt(request.getParameter("studentId"));

        logger.info("request posts :" + studentId);

        try {
            Student student = DbConnect.getStudent(studentId);
            logger.info("student :" + student);

            request.getSession().setAttribute("student", student);
            request.getRequestDispatcher("/studentDetails.jsp").forward(request,response);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentFirstName = request.getParameter("studentFirstName");
        String studentLastName = request.getParameter("studentLastName");
        logger.info("request get \nstudentFirstName: " + studentFirstName + "\nstudentLastName: " + studentLastName);

        try {
            List<Student> students = DbConnect.getStudentsByName(studentLastName, studentFirstName);
            logger.info("students: " + students);

            request.setAttribute("students", students);

            logger.info("forwarding to studentList.jsp");
            request.getRequestDispatcher("studentList/studentList.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Error fetching data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
