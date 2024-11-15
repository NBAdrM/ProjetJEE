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

public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /*
     * This method will redirect to the correct action (create, delete or modify)
     * and to the correct role (student or teacher)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String role = request.getParameter("role");
        String id = request.getParameter("id");
        if (action == null || action.isEmpty()) {
            throw new ServletException("Action is required");
        }

        //Redirect to the correct action
        switch (action) {
            case "create":
                dispatchRole(request, response, role, String.valueOf(-1)); //Set id to -1 to avoid null pointer exception and to indicate that it's a creation
                break;
            case "modify":
                verifyId(id);
                dispatchRole(request, response, role, id);
                break;
            case "delete" :
                doDelete(request, response); //Redirect to the doDelete method because HTML doesn't support DELETE method
                break;
            default:
                throw new ServletException("Invalid action");
        }
    }

    /*
     * This method will be called when a admin wants to delete a user
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        verifyId(id);

        //TODO : Make code when Adrien will have finished the database class
        request.setAttribute("success", "User deleted successfully");
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }

    /*
        * This method will dispatch to the correct page for the correct role (student or teacher)
        * Also, it will add the id to the request
        *
        * @param String role : The role of the user (student or teacher)
        * @param String id : The id of the user
        *
     */
    private void dispatchRole(HttpServletRequest request, HttpServletResponse response, String role, String id) throws ServletException, IOException {

        if (role == null || role.isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/admin.jsp").forward(request, response);
            return;
        }

        //Add id to the request
        request.setAttribute("id", id);

        if (role.equals("student")) {
            request.getRequestDispatcher("../student/studentForm.jsp").forward(request, response);
        } else if (role.equals("teacher")) {
            request.getRequestDispatcher("../teacher/teacherForm.jsp").forward(request, response);
        } else {
            throw new ServletException("Invalid role");
        }
    }

    /*
     * This method will verify if the id is not null or empty
     *
     * @param String id : The id to verify
     */
    private void verifyId(String id) throws ServletException {
        if (id == null || id.isEmpty()) {
            throw new ServletException("Id is required");
        }
    }
}