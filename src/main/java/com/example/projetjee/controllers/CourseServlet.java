package com.example.projetjee.controllers;

import com.example.projetjee.models.Course;
import com.example.projetjee.utils.DbConnnect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class CourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Récupérer les paramètres de la requête
        String name = request.getParameter("name");
        String yearString = request.getParameter("year");
        String teacherIdString = request.getParameter("teacherId");

        if (name == null || name.isEmpty() || yearString == null || yearString.isEmpty() || teacherIdString == null || teacherIdString.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(new Gson().toJson("Erreur : Les champs 'name', 'year' et 'teacherId' sont requis."));
            return;
        }

        int year;
        int teacherId;
        try {
            year = Integer.parseInt(yearString);
            teacherId = Integer.parseInt(teacherIdString);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(new Gson().toJson("Erreur : 'year' et 'teacherId' doivent être des entiers."));
            return;
        }

        try {
            // Ajouter le cours en utilisant les 3 paramètres attendus par la méthode addCourse
            int generatedId = DbConnnect.addCourse(name, year, teacherId);

            if (generatedId > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print(new Gson().toJson("Cours ajouté avec succès avec l'ID : " + generatedId));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(new Gson().toJson("Erreur : Le cours n'a pas pu être ajouté."));
            }
        } catch (SQLException | ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(new Gson().toJson("Erreur de la base de données : " + e.getMessage()));
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Récupérer l'ID du cours à partir de la requête
        String courseIdString = request.getParameter("courseId");

        if (courseIdString == null || courseIdString.isEmpty()) {
            // Si aucun ID n'est passé, renvoyer tous les cours
            String query = "SELECT * FROM courses";
            try (Connection connection = DbConnnect.initializeDatabase();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                // Liste de tous les cours
                Gson gson = new Gson();
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(gson.toJson(resultSet));
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(new Gson().toJson("Erreur de la base de données : " + e.getMessage()));
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Si un ID de cours est passé, récupérer le cours spécifique
            int courseId;
            try {
                courseId = Integer.parseInt(courseIdString);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(new Gson().toJson("Erreur : L'ID du cours doit être un entier."));
                return;
            }

            String query = "SELECT * FROM courses WHERE id = ?";
            try (Connection connection = DbConnnect.initializeDatabase();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, courseId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");

                    // Créer un objet de type Course et le renvoyer en JSON
                    //Course course = new Course(courseId, name, degree,teacherId);
                    response.setStatus(HttpServletResponse.SC_OK);
                    //out.print(new Gson().toJson(course));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(new Gson().toJson("Erreur : Cours non trouvé."));
                }
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(new Gson().toJson("Erreur de la base de données : " + e.getMessage()));
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
