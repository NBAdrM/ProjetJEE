package com.example.projetjee.controllers;

import com.example.projetjee.models.Course;
import com.example.projetjee.utils.DbConnect;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import com.google.gson.Gson;

public class EnrollmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EnrollmentServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer studentId = (Integer) session.getAttribute("userId");
        logger.info("Reçu une requête POST pour l'inscription.");

        if (studentId == null) {
            logger.warning("L'utilisateur n'est pas connecté. Redirection vers la page de connexion.");
            session.setAttribute("errorMessage", "Vous devez être connecté pour vous inscrire.");
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String courseIdStr = request.getParameter("courseId");
        logger.info("courseIdStr reçu : " + courseIdStr);

        if (courseIdStr == null || courseIdStr.isEmpty()) {
            logger.warning("L'ID du cours est vide ou manquant.");
            session.setAttribute("errorMessage", "Données invalides. Veuillez choisir un cours.");
            response.sendRedirect(request.getContextPath() + "/course/enrollment.jsp");
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdStr);
            logger.info("Conversion réussie : courseId = " + courseId);
            logger.info("Tentative d'inscription dans la base de données pour studentId = " + studentId);

            // Appel à la base de données
            DbConnect.addStudentFollowCourse(studentId, courseId);
            logger.info("Inscription réussie dans la base de données.");

            session.setAttribute("successMessage", "Inscription réussie !");
            logger.info("Message de succès défini dans la session.");
            response.sendRedirect(request.getContextPath() + "/course/enrollmentSuccess.jsp");
            logger.info("Redirection vers enrollmentSuccess.jsp effectuée avec succès.");

        } catch (NumberFormatException e) {
            logger.warning("Erreur de format de l'ID du cours : " + e.getMessage());
            session.setAttribute("errorMessage", "L'ID du cours est invalide.");
            response.sendRedirect(request.getContextPath() + "/course/enrollment.jsp");

        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Erreur lors de l'inscription en base de données : " + e.getMessage());
            session.setAttribute("errorMessage", "Une erreur est survenue. Veuillez réessayer.");
            response.sendRedirect(request.getContextPath() + "/course/enrollment.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Reçu une requête GET avec action : " + action);

        try {
            if ("getCoursesAndTeachers".equals(action)) {
                logger.info("Chargement des cours et des enseignants depuis la base de données.");
                List<Course> courses = DbConnect.getCourses();
                logger.info("Nombre de cours récupérés : " + courses.size());

                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(courses));
                logger.info("Données des cours renvoyées au client sous format JSON.");

            } else {
                logger.info("Chargement de la page JSP pour la gestion des cours.");
                List<Course> courses = DbConnect.getCourses();
                logger.info("Nombre de cours récupérés pour JSP : " + courses.size());

                request.setAttribute("courses", courses);
                request.getRequestDispatcher("/course/enrollment.jsp").forward(request, response);
                logger.info("Redirection vers enrollment.jsp réussie.");
            }
        } catch (SQLException e) {
            logger.severe("Erreur SQL lors du chargement des cours : " + e.getMessage());
            request.setAttribute("errorMessage", "Impossible de charger les cours. Veuillez réessayer.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);

        } catch (ClassNotFoundException e) {
            logger.severe("Erreur de classe lors du chargement des cours : " + e.getMessage());
            request.setAttribute("errorMessage", "Impossible de charger les cours. Veuillez réessayer.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}