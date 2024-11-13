package com.example.projetjee.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.example.projetjee.models.Teacher;

public class TeacherServlet {
    private static final long serialVersionUID = 1L;
    /*
     * This method will be called when a admin will submit the form
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        //TODO : generate a username from the first name and last name
        String username = request.getParameter("username");
        //TODO : generate a password
        String password = request.getParameter("password");
        int id =-1; //Initialize the id to -1 to avoid null pointer exception, it will be updated after the insertion in the database

        //Check if one field is empty
        if (lastName == null || lastName.isEmpty() || firstName == null || firstName.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty() || username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/teacherForm.jsp").forward(request, response);
        }
        //Insert data into the database
        //TODO : Make code when Adrien will have finished the database class

        //Create a teacher object
        //Teacher teacher = new Teacher(id, lastName, firstName, email, address, username, password);

        //Give the teacher object to the jsp page
        //request.setAttribute("teacher", teacher);
        request.setAttribute("success", "Teacher created successfully");
        request.getRequestDispatcher("/home").forward(request, response);
    }
}