package com.example.projetjee.controllers;

import com.example.projetjee.models.Grade;
import com.example.projetjee.utils.DbConnnect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GradeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("courseName");
        String gradeStr = request.getParameter("grade");

        try {
            double grade = Double.parseDouble(gradeStr);

            // Création de l'objet Grade
            Grade gradeObj = new Grade(studentId, courseId, courseName, grade);

            //DbConnnect.insertGrade(gradeObj);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");

        try {
            // Connexion à la base de données
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "root", "password");

            String query = "SELECT c.name, g.grade FROM grades g INNER JOIN courses c ON g.course_id = c.id WHERE g.student_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();

            response.setContentType("text/html");
            response.getWriter().println("<h1>Résultats</h1><ul>");

            while (rs.next()) {
                String courseName = rs.getString("name");
                double grade = rs.getDouble("grade");
                response.getWriter().println("<li>" + courseName + ": " + grade + "</li>");
            }

            response.getWriter().println("</ul>");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur lors de la consultation des résultats.");
        }
    }
}

