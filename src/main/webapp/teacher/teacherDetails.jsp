<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Détails d'un étudiant</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/courseCalendar.css">
</head>
<body>

<header>
  <nav>
    <div class="nav-center">
      <a href="<%= request.getContextPath() %>/home.jsp">Accueil</a>
      <a href="<%= request.getContextPath() %>/course/courseCalendar.jsp">Emploi du temps</a>
      <a href="<%= request.getContextPath() %>/listResultats">Résultats</a>
    </div>
    <div class="logout">
      <form action="<%= request.getContextPath() %>/logoutServlet" method="post">
        <button type="submit">Déconnexion</button>
      </form>
    </div>
  </nav>
</header>

<div class="main-content">
  <h1>Détails d'un étudiant</h1>

  <p>Nom : ${teacher.firstName} ${teacher.lastName}</p>
  <p>Email : ${teacher.email}</p>
  <p>Adresse : ${teacher.address}</p>
  <p>Actif : <c:choose>
    <c:when test="${teacher.active}">Oui</c:when>
    <c:otherwise>Non</c:otherwise>
  </c:choose>
  </p>

</div>
</body>
</html>
