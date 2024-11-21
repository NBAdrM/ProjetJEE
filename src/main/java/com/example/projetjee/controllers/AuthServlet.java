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
            // Check if username exists
            logger.info("Checking Username");
            if (DbConnnect.alreadyExisteUsername(username)) {
                // Get User ID
                int userId = DbConnnect.getUserIdByUsername(username);
                logger.info("UserID found: " + userId);

                // Check password
                if (DbConnnect.checkPassword(userId, password)) {
                    logger.info("Password OK");

                    // Creating a session for the authenticated user
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId);
                    session.setAttribute("username", username);
                    logger.info("Session created for user: " + username);

                    // Setting the response
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().println("Authentification réussie !");
                } else {
                    logger.info("Password NOT OK");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().println("Mot de passe incorrect !");
                }
            } else {
                logger.info("Username " + username + " not found");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("Utilisateur non trouvé !");
            }
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Erreur interne du serveur : " + e.getMessage());
        }
    }
}
