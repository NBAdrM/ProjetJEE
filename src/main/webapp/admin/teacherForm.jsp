<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Teacher Form</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/teacherForm.css">
</head>
<body>
<h2>Enregistrer un nouveau professeur</h2>

<!-- Affichage des messages d'erreur ou de succès -->
<c:if test="${not empty error}">
  <div style="color: red;">${error}</div>
</c:if>
<c:if test="${not empty success}">
  <div style="color: green;">${success}</div>
</c:if>

<form action="TeacherServlet" method="post">
  <label for="lastName">Nom de famille:</label><br>
  <input type="text" id="lastName" name="lastName" required><br><br>

  <label for="firstName">Prénom:</label><br>
  <input type="text" id="firstName" name="firstName" required><br><br>

  <label for="email">Email:</label><br>
  <input type="email" id="email" name="email" required><br><br>

  <label for="address">Adresse:</label><br>
  <input type="text" id="address" name="address" required><br><br>

  <input type="submit" value="Soumettre">
</form>

<a href="home.jsp">Retour à l'accueil</a>
</body>
</html>
