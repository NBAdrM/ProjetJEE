package com.example.projetjee.controllers;
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

public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /*
     * This method will redirect to the correct action (create, delete or modify)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String role = request.getParameter("role");
        String id = request.getParameter("id");

        if (action == null || action.isEmpty()) {
            throw new ServletException("Action is required");
        }

        switch (action) {
            case "create":
                createUser(request, response, role);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            case "modify":
                modifyUser(request, response, id);
                break;
            default:
                throw new ServletException("Invalid action");
        }
    }

    /*
     * This method will be called to delete a user
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            request.setAttribute("error", "Id is required");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }
        //TODO : Make code when Adrien will have finished the database class
    }

    /*
     * This method will be called to create a user and will redirect to the correct form
     */
    private void createUser(HttpServletRequest request, HttpServletResponse response, String role) throws ServletException, IOException {
        //Check if one field is empty
        if (role == null || role.isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }

        if (role.equals("student")) {
            request.getRequestDispatcher("/studentForm.jsp").forward(request, response);
        } else if (role.equals("teacher")) {
            request.getRequestDispatcher("/teacherForm.jsp").forward(request, response);
        } else {
            throw new ServletException("Invalid role");
        }
    }

    /*
     * This method will be called to modify a user
     */
    private void modifyUser(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {

        if (id == null || id.isEmpty()) {
            request.setAttribute("error", "Id is required");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }

        //TODO : Make code when Adrien will have finished the database class
    }
}