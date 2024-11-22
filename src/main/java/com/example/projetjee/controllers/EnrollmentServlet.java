package com.example.projetjee.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.example.projetjee.models.Course;

public class EnrollmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");

        try {
            // Connexion à la base de données
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "root", "password");

            String query = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);

            stmt.executeUpdate();
            response.getWriter().println("Inscription réussie.");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur lors de l'inscription.");
        }
    }