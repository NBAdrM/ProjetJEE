package com.example.projetjee.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.projetjee.utils.DbConnnect;

public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AuthServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("Request received: username = " + username);

        try {
            if (DbConnnect.alreadyExisteUsername(username)) {
                int userId = DbConnnect.getUserIdByUsername(username);
                logger.info("UserID found: " + userId);

                if (DbConnnect.checkPassword(userId, password)) {
                    logger.info("Password OK");

                    // Creating a session for the authenticated user
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId);
                    session.setAttribute("username", username);
                    session.setAttribute("role", DbConnnect.getRoleById(userId));
                    logger.info("Session created for user: " + username);

                    if (DbConnnect.getRoleById(userId).equals("admin")) {
                        logger.info("User is an admin");
                        response.sendRedirect("/ProjetJEE_war_exploded/admin/admin.jsp");
                    }else {
                        response.sendRedirect("/ProjetJEE_war_exploded/home.jsp");
                    }
                } else {
                    logger.info("Password NOT OK");
                    request.setAttribute("message", "Identifiant ou mot de passe incorrect !");
                    request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                }
            } else {
                logger.info("Username " + username + " not found");
                request.setAttribute("message", "Identifiant ou mot de passe incorrect !");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            request.setAttribute("message", "Erreur interne du serveur : " + e.getMessage());
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }
}

