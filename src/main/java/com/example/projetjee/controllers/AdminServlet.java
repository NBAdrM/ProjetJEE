package com.example.projetjee.controllers;
import com.example.projetjee.models.Student;
import com.example.projetjee.models.Teacher;
import com.example.projetjee.utils.DbConnect;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StudentServlet.class.getName());

    /*
     * This method will redirect to the correct action (create, delete or modify)
     * and to the correct role (student or teacher)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("Start student servlet");
        String action = request.getParameter("action");
        String role = request.getParameter("role");
        String id = request.getParameter("id");

        logger.info("request get \naction: " + action + "\nrole: " + role + "\nid: " + id);

        if (action == null || action.isEmpty()) {
            logger.warning("action is null or empty");
            throw new ServletException("Action is required");
        }

        //Redirect to the correct action
        try {
            switch (action) {
                case "create":
                    dispatchRole(request, response, role, String.valueOf(-1)); //Set id to -1 to avoid null pointer exception and to indicate that it's a creation
                    break;
                case "modify":
                    verifyId(id);
                    dispatchRole(request, response, role, id);
                    break;
                case "delete":
                    doDelete(request, response); //Redirect to the doDelete method because HTML doesn't support DELETE method
                    break;
                default:
                    logger.warning("unknown action: " + action);
                    throw new ServletException("Invalid action");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Error fetching data: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching data");
        }
    }
    /*
        * This method will be called when an admin wants to get the list of student or teacher
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start get");
        logger.info("Request URI: " + request.getRequestURI());
        logger.info("Query String: " + request.getQueryString());
        String role = request.getParameter("role");
        logger.info("request get \nrole: " + role);

        if (role == null || role.isEmpty()) {
            logger.warning("role is null or empty");
            throw new ServletException("Role is required");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        try {
            switch (role) {
                case "student":
                    List<Student> students = DbConnect.getStudents();
                    logger.info("Students list: " + students);
                    students = students.stream()
                            .filter(Student::getActive) //
                            .collect(Collectors.toList());
                    logger.info("Filtered Students list: " + students);
                    String studentJson = gson.toJson(students);
                    response.getWriter().write(studentJson);
                    break;

                case "teacher":
                    List<Teacher> teachers = DbConnect.getTeachers();
                    logger.info("Teachers list: " + teachers);
                    teachers = teachers.stream()
                            .filter(Teacher::getActive) //
                            .collect(Collectors.toList());
                    logger.info("Filtered Teachers list: " + teachers);
                    String teacherJson = gson.toJson(teachers);
                    response.getWriter().write(teacherJson);
                    break;

                default:
                    logger.warning("Unknown role: " + role);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid role");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Error fetching data: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching data");
        }
    }


    /*
     * This method will be called when an admin wants to delete a user
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Start delete");
        String id = request.getParameter("id");
        verifyId(id);

        try {
            DbConnect.deletePerson(Integer.parseInt(id));
        } catch (SQLException | ClassNotFoundException e) {
            logger.severe("Error deleting user: " + e.getMessage());
            throw new RuntimeException(e);
        }
        request.setAttribute("success", "User deleted successfully");
        response.sendRedirect(request.getContextPath() + "/admin/admin.jsp");
    }

    /*
        * This method will dispatch to the correct page for the correct role (student or teacher)
        * Also, it will add the id to the request
        *
        * @param String role : The role of the user (student or teacher)
        * @param String id : The id of the user
        *
     */
    private void dispatchRole(HttpServletRequest request, HttpServletResponse response, String role, String id) throws ServletException, IOException, SQLException, ClassNotFoundException {
        logger.info("Start dispatch role");

        //Add id to the request
        request.setAttribute("id", id);

        if (role.equals("student")) {
            Student student = DbConnect.getStudent(Integer.parseInt(id));
            request.setAttribute("student", student);
            request.getRequestDispatcher("/admin/studentForm.jsp").forward(request, response);
        } else if (role.equals("teacher")) {
            Teacher teacher = DbConnect.getTeacher(Integer.parseInt(id));
            request.setAttribute("teacher", teacher);
            request.getRequestDispatcher("/admin/teacherForm.jsp").forward(request, response);
        } else {
            logger.warning("unknown role: " + role);
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
            logger.warning("id is null or empty");
            throw new ServletException("Id is required");
        }
    }
}