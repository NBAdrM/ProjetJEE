<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Cours</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/courseServlet.css">
</head>
<body>
<h1>Gestion des Cours</h1>

<div class="panel">
    <h2 class="panel-header">Liste des Cours</h2>
    <div class="panel-content">
        <button id="loadCoursesButton">Charger tous les cours</button>
        <div id="allCoursesList"></div>
    </div>
</div>

<div class="panel">
    <h2 class="panel-header">Rechercher un cours par ID</h2>
    <div class="panel-content">
        <form id="getCourseForm">
            <label for="courseId">ID du Cours:</label>
            <input type="text" id="courseId" name="courseId" required>
            <button type="submit">Rechercher</button>
        </form>
        <div id="courseDetails"></div>
    </div>
</div>

<div class="panel">
    <h2 class="panel-header">Créer un cours</h2>
    <div class="panel-content">
        <form id="createCourseForm" method="post" action="${pageContext.request.contextPath}/course">
            <label for="courseName">Nom du Cours:</label>
            <input type="text" id="courseName" name="name" required>

            <label for="courseYear">Année:</label>
            <input type="number" id="courseYear" name="year" required>

            <label for="teacherId">ID du Professeur:</label>
            <input type="number" id="teacherId" name="teacherId" required>

            <button type="submit">Créer</button>
        </form>
    </div>
</div>

<div class="error-bar" id="errorBar">Erreur : Aucun cours trouvé.</div>

<div class="back-button-container">
    <button id="backButton" onclick="goBack()">Retour</button>
</div>

<script src="${pageContext.request.contextPath}/resources/js/courseServlet.js"></script>
</body>
</html>
