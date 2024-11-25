package com.example.projetjee.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.example.projetjee.models.Student;
import com.example.projetjee.utils.UserGenerator;
import com.example.projetjee.utils.DbConnnect;

public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentServlet.class.getName());
    /*
     * This method will be called when an admin will submit the form
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start student servlet");
        String id = request.getParameter("id");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String report = request.getParameter("report");

        logger.info("request give \nlastName: " + lastName + "\nfirstName: " + firstName + "\nemail: " + email + "\naddress: " + address + "\nreport: " + report);


        //Check if one field is empty
        if (lastName == null || lastName.isEmpty() || firstName == null || firstName.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty() ||  report == null || report.isEmpty()) {
            logger.warning("Invalid parameters");
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/studentForm.jsp").forward(request, response);
            return;
        }

        try {
            // Vérifier si on est en mode modification
            if (id != null && !id.isEmpty()) {
                // Modification d'un étudiant existant
                int idInt = Integer.parseInt(id);
                logger.info("Updating student with ID: " + idInt);

                Student student = new Student(idInt, lastName, firstName, email, address, null, null, Boolean.TRUE, report);
                DbConnnect.updateStudent(student.getId(),student.getFirstName(),student.getLastName(),student.getEmail(),student.getEmail(),Boolean.TRUE,student.getReport()); // Implémente cette méthode pour mettre à jour en base

                request.setAttribute("success", "Student updated successfully");
            } else {
                // Création d'un nouvel étudiant
                logger.info("Creating new student");

                String username = UserGenerator.generateUsername(firstName, lastName);
                String password = UserGenerator.generatePassword();
                logger.info("Generated username: " + username + " and password: " + password);

                Student student = new Student(-1, firstName, lastName, email, address, username, password, Boolean.TRUE, report);
                int newId = DbConnnect.addStudent(student.getFirstName(), student.getLastName(), student.getEmail(), student.getAddress(),
                        student.getUsername(), student.getPassword(), Boolean.TRUE, student.getReport());
                student.setId(newId);

                request.setAttribute("success", "Student created successfully");
            }

            // Rediriger vers la page admin
            response.sendRedirect(request.getContextPath() + "/admin/admin.jsp");

        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}