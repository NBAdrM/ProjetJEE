<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Cours</title>
    <link rel="stylesheet" href="courseServlet.css">
</head>
<body>
<h1>Gestion des Cours</h1>

<!-- Section pour charger tous les cours -->
<div>
    <h2>Liste des Cours</h2>
    <form action="<%= request.getContextPath()%>/course" action="get">

    </form>
    <button id="loadCoursesButton">Charger tous les cours</button>
    <div id="allCoursesList">
        <%-- L'endroit où la liste des cours sera affichée --%>
    </div>
</div>

<!-- Section pour rechercher un cours par ID -->
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

<!-- Section pour créer un cours -->
<div>
    <h2>Créer un cours</h2>
    <form id="createCourseForm" method="post" action="${pageContext.request.contextPath}/course">
        <label for="courseName">Nom du Cours:</label>
        <input type="text" id="courseName" name="name" required><br>

        <label for="courseYear">Année:</label>
        <input type="number" id="courseYear" name="year" required><br>

        <label for="teacherId">ID du Professeur:</label>
        <input type="number" id="teacherId" name="teacherId" required><br>

        <button type="submit">Créer</button>
    </form>
</div>

<script>
    // Charger la liste des cours
    document.getElementById('loadCoursesButton').onclick = function () {
        fetch('/projetjee/courseServlet')
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Erreur inconnue lors de la récupération des cours');
                    });
                }
                return response.json();
            })
            .then(data => {
                let courseListHtml = '<h3>Liste des Cours</h3><ul>';
                data.forEach(course => {
                    courseListHtml += '<li>' + course.name + '</li>';
                });
                courseListHtml += '</ul>';
                document.getElementById('allCoursesList').innerHTML = courseListHtml;
            })
            .catch(error => {
                document.getElementById('allCoursesList').innerHTML = `<p style="color:red;">Erreur : ${error.message}</p>`;
                console.error('Erreur:', error);
            });
    };

    // Rechercher un cours par ID
    document.getElementById('getCourseForm').onsubmit = function (e) {
        e.preventDefault();
        var courseId = document.getElementById('courseId').value;
        fetch('/projetjee/courseServlet?courseId=' + courseId)
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Erreur inconnue lors de la récupération des détails du cours');
                    });
                }
                return response.json();
            })
            .then(data => {
                let courseDetailsHtml = '<h3>Détails du Cours</h3>';
                if (data.name) {
                    courseDetailsHtml += '<p>Nom: ' + data.name + '</p>';
                    courseDetailsHtml += '<p>Description: ' + data.description + '</p>';
                } else {
                    courseDetailsHtml += `<p style="color:red;">Erreur : ${data.message}</p>`;
                }
                document.getElementById('courseDetails').innerHTML = courseDetailsHtml;
            })
            .catch(error => {
                document.getElementById('courseDetails').innerHTML = `<p style="color:red;">Erreur : ${error.message}</p>`;
                console.error('Erreur:', error);
            });
    };
</script>
</body>
</html>
