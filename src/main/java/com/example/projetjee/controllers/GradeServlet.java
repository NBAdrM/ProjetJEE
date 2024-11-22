package com.example.projetjee.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Annotation pour définir le mapping de la servlet
@WebServlet("/grades")
public class GradeServlet extends HttpServlet {

    private GradeService gradeService;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialiser le service (par exemple, avec une couche DAO ou Hibernate)
        gradeService = new GradeService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres depuis le formulaire ou la requête
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");
        String gradeStr = request.getParameter("grade");

        try {
            double grade = Double.parseDouble(gradeStr);
            // Enregistrer la note via le service
            boolean success = gradeService.saveGrade(studentId, courseId, grade);

            // Répondre à l'utilisateur
            if (success) {
                response.getWriter().println("Note enregistrée avec succès !");
            } else {
                response.getWriter().println("Échec de l'enregistrement de la note.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Note invalide. Veuillez entrer un nombre.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'ID de l'étudiant depuis la requête
        String studentId = request.getParameter("studentId");

        if (studentId == null || studentId.isEmpty()) {
            response.getWriter().println("Veuillez fournir un ID étudiant.");
            return;
        }

        // Obtenir les résultats via le service
        List<Grade> grades = gradeService.getGradesByStudent(studentId);

        // Générer une réponse HTML pour afficher les résultats
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Résultats pour l'étudiant ID: " + studentId + "</h1>");
        if (grades.isEmpty()) {
            out.println("<p>Aucun résultat trouvé.</p>");
        } else {
            out.println("<table border='1'>");
            out.println("<tr><th>Cours</th><th>Note</th></tr>");
            for (Grade grade : grades) {
                out.println("<tr><td>" + grade.getCourseName() + "</td><td>" + grade.getGrade() + "</td></tr>");
            }
            out.println("</table>");
        }
        out.println("</body></html>");
    }
}
