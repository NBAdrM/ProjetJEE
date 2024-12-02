<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Cours</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/courseServlet.css">
    <script src="${pageContext.request.contextPath}/resources/js/courseServlet.js" defer></script>
</head>
<body>
<h1>Gestion des Cours</h1>

<!-- Panneau pour charger tous les cours -->
<div class="panel">
    <h2>Liste des Cours</h2>
    <div class="panel-content">
        <button id="loadCoursesButton">Charger tous les cours</button>
        <div id="allCoursesList"></div>
    </div>
</div>

<!-- Panneau pour rechercher un cours par ID -->
<div class="panel">
    <h2>Rechercher un cours par ID</h2>
    <div class="panel-content">
        <form id="getCourseForm">
            <label for="courseId">ID du Cours:</label>
            <input type="text" id="courseId" name="courseId" required>
            <button type="submit">Rechercher</button>
        </form>
        <div id="courseDetails"></div>
    </div>
</div>

<!-- Panneau pour créer un cours -->
<div class="panel">
    <h2>Créer un cours</h2>
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

<!-- Barre d'erreur -->
<div class="error-bar" id="errorBar" style="display: none;"></div>
<a href="${pageContext.request.contextPath}/home.jsp" class="a">Retour</a>

</body>
</html>
