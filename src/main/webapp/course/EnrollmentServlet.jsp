<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Inscription à un Cours</title>
  <script>
    // Charger les cours et enseignants au chargement de la page
    window.onload = function () {
      fetch('${pageContext.request.contextPath}/enrollment?action=getCoursesAndTeachers')
              .then(response => response.json())
              .then(data => {
                const courseSelect = document.getElementById('courseId');

                // Ajouter les options pour chaque cours et professeur
                data.forEach(item => {
                  const option = document.createElement('option');
                  option.value = item.courseId; // ID du cours
                  option.textContent = item.courseName + " (Prof: " + item.teacherName + ")";
                  courseSelect.appendChild(option);
                });
              })
              .catch(error => {
                console.error('Erreur lors du chargement des cours :', error);
                document.getElementById('error').textContent =
                        "Impossible de charger les cours. Veuillez réessayer plus tard.";
              });
    };
  </script>
</head>
<body>
<h1>Inscription à un Cours</h1>

<!-- Affichage du message de succès -->
<div style="color: green; font-weight: bold;">
  <c:if test="${not empty successMessage}">
    ${successMessage}
  </c:if>
</div>

<!-- Affichage du message d'erreur si l'inscription échoue -->
<div style="color: red; font-weight: bold;">
  <c:if test="${not empty errorMessage}">
    ${errorMessage}
  </c:if>
</div>

<!-- Formulaire d'inscription -->
<form id="enrollForm" method="post" action="${pageContext.request.contextPath}/enrollment">
  <label>Choisissez un cours :</label>
  <select id="courseId" name="courseId" required>
    <!-- Les options seront ajoutées dynamiquement ici -->
  </select><br>

  <button type="submit">Inscrire</button>
</form>

<!-- Bouton pour revenir à l'accueil -->
<a href="${pageContext.request.contextPath}/home.jsp">
  <button>Revenir à l'accueil</button>
</a>

</body>
</html>
