package com.example.projetjee.controllers;

import com.example.projetjee.utils.DbConnect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class EnrollmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EnrollmentServlet.class.getName());

    // Traitement de la demande POST pour l'inscription
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération de l'ID de l'étudiant depuis la session
        HttpSession session = request.getSession();
        Integer studentId = (Integer) session.getAttribute("studentId");

        // Vérification si l'étudiant est connecté
        if (studentId == null) {
            request.setAttribute("errorMessage", "Vous devez être connecté pour vous inscrire.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Récupération de l'ID du cours depuis la requête
        String courseIdStr = request.getParameter("courseId");

        // Vérification si l'ID du cours est valide
        if (courseIdStr == null || courseIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Données invalides. Veuillez choisir un cours.");
            request.getRequestDispatcher("/enrollment.jsp").forward(request, response);
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdStr); // Conversion de l'ID du cours en entier

            // Connexion à la base de données et inscription de l'étudiant au cours
            try (Connection connection = DbConnect.initializeDatabase()) {
                String enrollQuery = "INSERT INTO Student_has_Course (Student_id, course_id) VALUES (?, ?)";
                PreparedStatement enrollStmt = connection.prepareStatement(enrollQuery);
                enrollStmt.setInt(1, studentId); // ID de l'étudiant
                enrollStmt.setInt(2, courseId);  // ID du cours

                int rowsAffected = enrollStmt.executeUpdate();
                if (rowsAffected > 0) {
                    request.setAttribute("successMessage", "Inscription réussie !");
                    request.getRequestDispatcher("/enrollmentSuccess.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Échec de l'inscription. Veuillez réessayer.");
                    request.getRequestDispatcher("/EnrollmentServlet.jsp").forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException e) {
                logger.severe("Erreur lors de l'inscription : " + e.getMessage());
                request.setAttribute("errorMessage", "Une erreur est survenue pendant l'inscription. Veuillez réessayer.");
                request.getRequestDispatcher("/EnrollmentServlet.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Données invalides. L'ID du cours est incorrect.");
            request.getRequestDispatcher("/EnrollmentServlet.jsp").forward(request, response);
        }
    }

    // Traitement de la demande GET pour récupérer les cours et enseignants
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("getCoursesAndTeachers".equals(action)) {
            logger.info("Demande GET reçue pour récupérer les cours et enseignants");

            try (Connection connection = DbConnect.initializeDatabase()) {
                String query = "SELECT c.id AS course_id, c.name AS course_name, p.first_name AS teacher_first_name, p.last_name AS teacher_last_name " +
                        "FROM Course c JOIN Teacher t ON c.Teacher_Person_id = t.id JOIN Person p ON t.Person_id = p.id";
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                StringBuilder jsonBuilder = new StringBuilder();
                jsonBuilder.append("[");

                while (rs.next()) {
                    if (jsonBuilder.length() > 1) jsonBuilder.append(",");
                    jsonBuilder.append("{")
                            .append("\"courseId\":").append(rs.getInt("course_id")).append(",")
                            .append("\"courseName\":\"").append(rs.getString("course_name")).append("\",")
                            .append("\"teacherName\":\"").append(rs.getString("teacher_first_name"))
                            .append(" ").append(rs.getString("teacher_last_name")).append("\"")
                            .append("}");
                }

                jsonBuilder.append("]");
                response.setContentType("application/json");
                response.getWriter().write(jsonBuilder.toString());
            } catch (SQLException | ClassNotFoundException e) {
                logger.severe("Erreur lors de la récupération des cours et enseignants : " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}
