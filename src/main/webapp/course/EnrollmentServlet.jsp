<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Inscription à un Cours</title>
  <script> const contextPath = "${pageContext.request.contextPath}"; </script>
  <script src="${pageContext.request.contextPath}/resources/js/initCourse.js"></script>
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
