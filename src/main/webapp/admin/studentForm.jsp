<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Student Form</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/studentForm.css">
</head>
<body>
<h2>Enregistrer un nouveau étudiant</h2>

<!-- Display error or success messages -->
<c:if test="${not empty error}">
  <div style="color: red;">${error}</div>
</c:if>
<c:if test="${not empty success}">
  <div style="color: green;">${success}</div>
</c:if>

<form action="StudentServlet" method="post">
  <label for="lastName">Nom de famille:</label><br>
  <input type="text" id="lastName" name="lastName" required><br><br>

  <label for="firstName">Prénom:</label><br>
  <input type="text" id="firstName" name="firstName" required><br><br>

  <label for="email">Email:</label><br>
  <input type="email" id="email" name="email" required><br><br>

  <label for="address">Adresse:</label><br>
  <input type="text" id="address" name="address" required><br><br>

  <label for="report">Rapport:</label><br>
  <textarea id="report" name="report" required></textarea><br><br>

  <input type="submit" value="Soumettre">
</form>

<a href="home.jsp">Retour accueil</a>
</body>
</html>
