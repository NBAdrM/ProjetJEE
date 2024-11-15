package com.example.projetjee.controllers;
import com.example.projetjee.utils.DbConnnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.example.projetjee.models.Student;
import com.example.projetjee.utils.UserGenerator;
import com.example.projetjee.utils.DbConnnect;

public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /*
     * This method will be called when an admin will submit the form
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String report = request.getParameter("report");
        int id =-1; //Initialize the id to -1 to avoid null pointer exception, it will be updated after the insertion in the database

        //Check if one field is empty
        if (lastName == null || lastName.isEmpty() || firstName == null || firstName.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty() ||  report == null || report.isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/studentForm.jsp").forward(request, response);
            return;
        }

        try {
            //Generate Username and password and insert into the database
            String username = UserGenerator.generateUsername(firstName, lastName);
            String password = UserGenerator.generatePassword();

            //Student student = new Student(id, lastName, firstName, email, address, username, password, report);

            //DbConnnect.addStudent(student.getfirstName,student.getlastName,student.getEmail,student.getAddress,student.getUsername,student.getPassword, student.getReport);

            //Give the student object to the jsp page
            //request.setAttribute("student", student);
            request.setAttribute("success", "Student created successfully");
            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}