<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.models.Student" %>
<%@ page import="com.example.projetjee.models.Teacher" %>
<!DOCTYPE html>
<html>
<head>
    <title>Panneau Administration</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/admin.css">
    <script src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
</head>
<body>
<h1>Panneau d'administration</h1>

<!-- Boutons pour ajouter un nouvel utilisateur -->
<div style="text-align: center; margin-bottom: 20px;">
    <a href="${pageContext.request.contextPath}/admin/studentForm.jsp" class="button">Ajouter un élève</a>
    <a href="${pageContext.request.contextPath}/admin/teacherForm.jsp" class="button">Ajouter un professeur</a>
</div>

<!-- Filtre pour afficher les élèves ou les professeurs -->
<div class="filter-container" style="margin-bottom: 20px;">
    <label for="filter">Filtrer par rôle : </label>
    <select id="filter" onchange="filterTable()">
        <option> </option>
        <option value="student">Élèves</option>
        <option value="teacher">Professeurs</option>
    </select>
</div>

<!-- Tableau des utilisateurs -->
<table id="userTable" border="1" cellspacing="0" cellpadding="10" style="width: 100%; text-align: left;">
    <thead>
    <tr>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Rôle</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <!-- Les lignes seront ajoutées dynamiquement ici -->
    </tbody>
</table>
    <div class="logout">
        <form action="${pageContext.request.contextPath}/logoutServlet" method="post">
            <button type="submit">Se déconnecter</button>
        </form>
    </div>
</body>
</html>
