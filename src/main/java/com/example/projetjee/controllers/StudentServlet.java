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
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String report = request.getParameter("report");
        int id =-1; //Initialize the id to -1 to avoid null pointer exception, it will be updated after the insertion in the database
        logger.info("request give \nlastName: " + lastName + "\nfirstName: " + firstName + "\nemail: " + email + "\naddress: " + address + "\nreport: " + report);


        //Check if one field is empty
        if (lastName == null || lastName.isEmpty() || firstName == null || firstName.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty() ||  report == null || report.isEmpty()) {
            logger.warning("Invalid parameters");
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/studentForm.jsp").forward(request, response);
            return;
        }

        try {
            //Generate Username and password and insert into the database
            logger.info("Generating username and password");
            String username = UserGenerator.generateUsername(firstName, lastName);
            String password = UserGenerator.generatePassword();


            logger.info("Generating student instance");
            Student student = new Student(id, lastName, firstName, email, address, username, password, true, report);
            logger.info(student.toString());

            //TODO : Mettre à jour addStudent pour inclure active
            logger.info("Adding student to database");
            id = DbConnnect.addStudent(student.getFirstName(),student.getLastName(),student.getEmail(),student.getAddress(),student.getUsername(),student.getPassword(), student.getReport());
            logger.info("successfully added student to the database");

            //Update the id of the student
            logger.info("Update ID of the student, id :" + id );
            student.setId(id);

            //Give the student object to the jsp page
            request.setAttribute("student", student);
            request.setAttribute("success", "Student created successfully");
            logger.info("Redirecting to home.jsp");
            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}