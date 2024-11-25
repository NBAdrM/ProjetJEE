package com.example.projetjee.controllers;

import com.example.projetjee.models.Course;
import com.example.projetjee.utils.DbConnnect;

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
        Integer studentId = (Integer) session.getAttribute("studentId");

        if (studentId == null) {
            session.setAttribute("errorMessage", "Vous devez être connecté pour vous inscrire.");
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String courseIdStr = request.getParameter("courseId");
        if (courseIdStr == null || courseIdStr.isEmpty()) {
            session.setAttribute("errorMessage", "Données invalides. Veuillez choisir un cours.");
            response.sendRedirect(request.getContextPath() + "/enrollment.jsp");
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdStr);
            logger.info("Inscription de l'étudiant ID: " + studentId + " au cours ID: " + courseId);
            DbConnnect.addStudentFollowCourse(studentId, courseId);
            session.setAttribute("successMessage", "Inscription réussie !");
            response.sendRedirect(request.getContextPath() + "/enrollmentSuccess.jsp");

        } catch (NumberFormatException e) {
            logger.warning("Erreur de format : " + e.getMessage());
            session.setAttribute("errorMessage", "L'ID du cours est invalide.");
            response.sendRedirect(request.getContextPath() + "/enrollment.jsp");

        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Erreur lors de l'inscription : " + e.getMessage());
            session.setAttribute("errorMessage", "Une erreur est survenue. Veuillez réessayer.");
            response.sendRedirect(request.getContextPath() + "/enrollment.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Reçu requête GET avec action : " + action);

        try {
            if ("getCoursesAndTeachers".equals(action)) {
                logger.info("Chargement des cours et des enseignants.");
                List<Course> courses = DbConnnect.getCourses();

                logger.info("Nombre de cours récupérés : " + courses.size());
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(courses));
            } else {
                logger.info("Chargement de la page JSP pour la gestion des cours.");
                List<Course> courses = DbConnnect.getCourses();

                logger.info("Nombre de cours récupérés pour JSP : " + courses.size());
                request.setAttribute("courses", courses);
                request.getRequestDispatcher("/courses.jsp").forward(request, response);
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
