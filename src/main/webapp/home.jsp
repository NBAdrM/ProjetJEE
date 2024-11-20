<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.projetjee.models.Student, com.example.projetjee.models.Teacher" %>
<%
    String role = (String) session.getAttribute("role");
    Object user = request.getAttribute("user");

    if (role == null || user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String firstName = "";
    String lastName = "";

    if ("student".equals(role) && user instanceof Student) {
        Student student = (Student) user;
        firstName = student.getFirstName();
        lastName = student.getLastName();
    } else if ("teacher".equals(role) && user instanceof Teacher) {
        Teacher teacher = (Teacher) user;
        firstName = teacher.getFirstName();
        lastName = teacher.getLastName();
    } else {
        response.sendRedirect("login.jsp");
        return;
    }

    String roleClass = "student".equals(role) ? "background-student" : "background-teacher";
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/home.css">
</head>
<body class="<%= roleClass %>">
<h1 class="header">Bienvenue sur CY Note</h1>

<div class="content">
    <h2>Bienvenue, <%= firstName %> <%= lastName %>!</h2>
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
</div>
</body>
</html>