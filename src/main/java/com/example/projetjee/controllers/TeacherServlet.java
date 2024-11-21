package com.example.projetjee.controllers;
import com.example.projetjee.utils.DbConnnect;
import com.example.projetjee.utils.UserGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;


import com.example.projetjee.models.Teacher;
import com.example.projetjee.utils.DbConnnect;

public class TeacherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TeacherServlet.class.getName());
    /*
     * This method will be called when an admin will submit the form to create a teacher
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start TeacherServlet");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        int id =-1; //Initialize the id to -1 to avoid null pointer exception, it will be updated after the insertion in the database
        logger.info("request give \nlastName: " + lastName + "\nfirstName: " + firstName + "\nemail: " + email + "\naddress: " + address);

        //Check if one field is empty
        if (lastName == null || lastName.isEmpty() || firstName == null || firstName.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty()) {
            logger.warning("Invalid parameters");
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/teacherForm.jsp").forward(request, response);
            return;
        }

        try {
            //Generate Username and password and insert into the database
            logger.info("Generating username and password");
            String username = UserGenerator.generateUsername(firstName, lastName);
            String password = UserGenerator.generatePassword();
            logger.info("Generated username: " + username + " and password: " + password);

            logger.info("Generating teacher instance");
            Teacher teacher = new Teacher(id, lastName, firstName, email, address, username, password,Boolean.TRUE);
            logger.info(teacher.toString());

            logger.info("Adding teacher to database");
            id = DbConnnect.addTeacher(teacher.getFirstName(),teacher.getLastName(),teacher.getEmail(),teacher.getAddress(),teacher.getUsername(),teacher.getPassword(),Boolean.TRUE);
            logger.info("successfully added teacher to the database");

            //Update the id of the teacher
            logger.info("Update ID of the teacher, id :" + id);
            teacher.setId(id);

            //Give the teacher object to the jsp page
            request.setAttribute("teacher", teacher);
            request.setAttribute("success", "Teacher created successfully");
            logger.info("Redirect to home");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("SQL Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /*
     * This method will be called when we want to get info from one or multiple teachers
     * If the id is -1, we will get all the teachers
     * If the id is a number, we will get the teacher with this id
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            logger.warning("Invalid parameters");
            request.setAttribute("error", "ID is required");
            request.getRequestDispatcher("/teacherForm.jsp").forward(request, response);
            return;
        }

        if (id.equals("-1")) {
            //Get all teacher in database
            //DbConnnect.getTeacher()
            int i = 0;

        }else {
            //Get the teacher with the id
            //DbConnect.getTeacher(id)
            int i = 1;
        }

    }
}