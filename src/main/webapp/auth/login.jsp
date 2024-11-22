<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Redirection si l'utilisateur est déjà connecté
    if (session != null && session.getAttribute("username") != null) {
        response.sendRedirect("home.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Page de connexion</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/login.css">
</head>
<body>

<h1>CY Note</h1>

<form action="${pageContext.request.contextPath}/authServlet" method="post">
    <label for="username">Nom d'utilisateur :</label>
    <input type="text" id="username" name="username" required>
    <br><br>

    <label for="password">Mot de passe :</label>
    <input type="password" id="password" name="password" required>
    <br><br>

    <button type="submit">Se connecter</button>
</form>

<!-- Affichage conditionnel du message d'erreur -->
<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<p style="color: red; font-weight: bold; margin-top: 20px;"><%= message %></p>
<%
    }
%>

</body>
</html>
