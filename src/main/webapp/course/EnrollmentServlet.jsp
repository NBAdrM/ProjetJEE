<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Gestion des Inscriptions</title>
  <link rel="stylesheet" href="EnrollmentServlet.css">
</head>
<body>
<h1>Gestion des Inscriptions</h1>

<div>
  <h2>Inscrire un étudiant</h2>
  <form id="enrollForm" method="post" action="${pageContext.request.contextPath}/EnrollmentServlet">
    <label for="studentId">ID Étudiant:</label>
    <input type="text" id="studentId" name="studentId" required><br>

    <label for="courseId">ID du Cours:</label>
    <input type="text" id="courseId" name="courseId" required><br>

    <button type="submit">Inscrire</button>
  </form>
</div>

<div>
  <h2>Consulter les cours d'un étudiant</h2>
  <form id="getCoursesForm" method="get" action="/projetjee/enrollments/student">
    <label for="queryStudentId">ID Étudiant:</label>
    <input type="text" id="queryStudentId" name="studentId" required><br>

    <button type="submit">Rechercher</button>
  </form>
  <div id="coursesList">
    <%-- L'endroit où les résultats des cours seront affichés --%>
  </div>
</div>

<script>
  document.getElementById('getCoursesForm').onsubmit = function (e) {
    e.preventDefault();
    var studentId = document.getElementById('queryStudentId').value;
    fetch('/projetjee/enrollments/student/' + studentId)
            .then(response => response.json())
            .then(data => {
              let courseListHtml = '<h3>Liste des Cours</h3><ul>';
              data.forEach(course => {
                courseListHtml += '<li>' + course.name + '</li>';
              });
              courseListHtml += '</ul>';
              document.getElementById('coursesList').innerHTML = courseListHtml;
            })
            .catch(error => {
              document.getElementById('coursesList').innerHTML = '<p>Erreur lors de la récupération des données</p>';
              console.error('Erreur:', error);
            });
  };
</script>
</body>
</html>
