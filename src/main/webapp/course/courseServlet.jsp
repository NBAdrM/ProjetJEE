<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Cours</title>
    <link rel="stylesheet" href="courseServlet.css">
</head>
<body>
<h1>Gestion des Cours</h1>

<div>
    <h2>Liste des Cours</h2>
    <button id="loadCoursesButton">Charger tous les cours</button>
    <div id="allCoursesList">
        <%-- L'endroit où la liste des cours sera affichée --%>
    </div>
</div>

<div>
    <h2>Rechercher un cours par ID</h2>
    <form id="getCourseForm">
        <label for="courseId">ID du Cours:</label>
        <input type="text" id="courseId" name="courseId" required><br>

        <button type="submit">Rechercher</button>
    </form>
    <div id="courseDetails">
        <%-- L'endroit où les détails du cours seront affichés --%>
    </div>
</div>

<div>
    <h2>Créer un cours</h2>
    <form id="createCourseForm" method="post" action="${pageContext.request.contextPath}/courseServlet">
        <label for="courseName">Nom du Cours:</label>
        <input type="text" id="courseName" name="name" required><br>

        <label for="courseDescription">Description:</label>
        <input type="text" id="courseDescription" name="description" required><br>

        <button type="submit">Créer</button>
    </form>
</div>

<script>
    document.getElementById('loadCoursesButton').onclick = function () {
        fetch('/projetjee/courses/')
            .then(response => response.json())
            .then(data => {
                let courseListHtml = '<h3>Liste des Cours</h3><ul>';
                data.forEach(course => {
                    courseListHtml += '<li>' + course.name + '</li>';
                });
                courseListHtml += '</ul>';
                document.getElementById('allCoursesList').innerHTML = courseListHtml;
            })
            .catch(error => {
                document.getElementById('allCoursesList').innerHTML = '<p>Erreur lors de la récupération des données</p>';
                console.error('Erreur:', error);
            });
    };

    document.getElementById('getCourseForm').onsubmit = function (e) {
        e.preventDefault();
        var courseId = document.getElementById('courseId').value;
        fetch('/projetjee/courses/' + courseId)
            .then(response => response.json())
            .then(data => {
                let courseDetailsHtml = '<h3>Détails du Cours</h3>';
                courseDetailsHtml += '<p>Nom: ' + data.name + '</p>';
                courseDetailsHtml += '<p>Description: ' + data.description + '</p>';
                document.getElementById('courseDetails').innerHTML = courseDetailsHtml;
            })
            .catch(error => {
                document.getElementById('courseDetails').innerHTML = '<p>Erreur lors de la récupération des données</p>';
                console.error('Erreur:', error);
            });
    };
</script>
</body>
</html>
