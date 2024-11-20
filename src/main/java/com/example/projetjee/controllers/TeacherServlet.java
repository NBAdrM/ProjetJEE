package com.example.projetjee.controllers;
import com.example.projetjee.utils.DbConnnect;
import com.example.projetjee.utils.UserGenerator;
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
import com.example.projetjee.utils.DbConnnect;

public class TeacherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    /*
     * This method will be called when an admin will submit the form
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        int id =-1; //Initialize the id to -1 to avoid null pointer exception, it will be updated after the insertion in the database

        //Check if one field is empty
        if (lastName == null || lastName.isEmpty() || firstName == null || firstName.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/teacherForm.jsp").forward(request, response);
            return;
        }

        try {
            //Generate Username and password and insert into the database
            String username = UserGenerator.generateUsername(firstName, lastName);
            String password = UserGenerator.generatePassword();

            //Teacher teacher = new Teacher(id, lastName, firstName, email, address, username, password, report);

            //DbConnnect.addTeacher(teacher.getfirstName,teacher.getlastName,teacher.getEmail,teacher.getAddress,teacher.getUsername,teacher.getPassword,teacher.getReport);

            //Give the teacher object to the jsp page
            //request.setAttribute("teacher", teacher);
            request.setAttribute("success", "Teacher created successfully");
            request.getRequestDispatcher("/home").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            request.setAttribute("error", "ID is required");
            request.getRequestDispatcher("/teacherForm.jsp").forward(request, response);
            return;
        }

        if (id.equals("-1")) {
            //Get all teacher in database
            //DbConnnect.getTeacher()

        }else {
            //DbConnect.getTeacher(id)
        }

    }
}