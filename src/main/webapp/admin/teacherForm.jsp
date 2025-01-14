<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>
    <%
      String id = (String) request.getAttribute("id");
      out.print(id != null ? "Modifier le professeur" : "Créer un nouveau professeur");
    %>
  </title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/teacherForm.css">
  <!-- Ajout du style pour le bouton (je n'arrive pas a le placer dans le css et à le faire apparaitre-->
  <style>
    .a {
      position: fixed;
      top: 10px;
      left: 10px;
      font-weight: bold;
      color: #333333;
      text-decoration: none;
      background-color: #ffffff;
      padding: 5px 10px;
      border: 1px solid #333333;
      border-radius: 4px;
      z-index: 1000;
    }
    .a:hover {
      background-color: #5982C2;
      color: #ffffff;
    }
  </style>
</head>
<body>
<h2>
  <%
    out.print(id != null ? "Modifier le professeur" : "Créer un nouveau professeur");
  %>
</h2>

<!-- Affichage des messages d'erreur ou de succès -->
<c:if test="${not empty error}">
  <div style="color: red;">${error}</div>
</c:if>
<c:if test="${not empty success}">
  <div style="color: green;">${success}</div>
</c:if>

<form action="${pageContext.request.contextPath}/teacher" method="post">
  <input type="hidden" name="id" value="${id}">
  <label for="lastName">Nom de famille:</label><br>
  <input type="text" id="lastName" name="lastName" value="${teacher.lastName}" required><br><br>

  <label for="firstName">Prénom:</label><br>
  <input type="text" id="firstName" name="firstName" value="${teacher.firstName}" required><br><br>

  <label for="email">Email:</label><br>
  <input type="email" id="email" name="email" value="${teacher.email}" required><br><br>

  <label for="address">Adresse:</label><br>
  <input type="text" id="address" name="address" value="${teacher.address}" required><br><br>

  <input type="submit" value="Soumettre">
</form>

<a href="${pageContext.request.contextPath}/admin/admin.jsp" class="a">Retour</a>
</body>
</html>
