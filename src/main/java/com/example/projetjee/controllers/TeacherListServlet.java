
package com.example.projetjee.controllers;

import com.example.projetjee.models.Teacher;
import com.example.projetjee.utils.DbConnect;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class TeacherListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        logger.info("POST /TeacherList");

        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        logger.info("request posts :" + teacherId);

        try {
            Teacher teacher = DbConnect.getTeacher(teacherId);
            logger.info("teacher :" + teacher);

            request.getSession().setAttribute("teacher", teacher);
            request.getRequestDispatcher("teacher/teacherDetails.jsp").forward(request,response);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teacherFirstName = request.getParameter("teacherFirstName");
        String teacherLastName = request.getParameter("teacherLastName");
        logger.info("request get \nteacherFirstName: " + teacherFirstName + "\nteacherLastName: " + teacherLastName);

        try {
            List<Teacher> teachers = DbConnect.getTeachersByName(teacherLastName, teacherFirstName);
            logger.info("teachers: " + teachers);

            request.setAttribute("teachers", teachers);

            logger.info("forwarding to teacherList.jsp");
            request.getRequestDispatcher("teacher/teacherList.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Error fetching data: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
