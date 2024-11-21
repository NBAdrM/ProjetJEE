<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Vérifiez si une session existe
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("auth/login.jsp");
        return;
    }

    // Récupérez les attributs de session
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");

    // Définissez une classe CSS en fonction du rôle
    String roleClass = "student".equals(role) ? "background-student" : "background-teacher";
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta c harset="UTF-8">
    <title>Accueil</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/home.css">
</head>
<body class="<%= roleClass %>">
<h1 class="header">Bienvenue sur CY Note</h1>

<div class="content">
    <h2>Bienvenue, <%= username %>!</h2>
    <p>Choisissez une des options ci-dessous :</p>

    <div class="button-container">
        <% if ("student".equals(role)) { %>
        <a href="studentGrades.jsp" class="button">Consulter mes notes</a>
        <a href="studentSchedule.jsp" class="button">Consulter mon emploi du temps</a>
        <% } else if ("teacher".equals(role)) { %>
        <a href="teacherGrades.jsp" class="button">Gérer les notes des étudiants</a>
        <a href="teacherSchedule.jsp" class="button">Consulter mon emploi du temps</a>
        <% } %>
    </div>

    <div class="logout">
        <form action="${pageContext.request.contextPath}/logoutServlet" method="post">
            <button type="submit">Se déconnecter</button>
        </form>
    </div>
</div>
</body>
</html>
