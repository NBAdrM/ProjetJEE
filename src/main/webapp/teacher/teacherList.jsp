<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des étudiants</title>
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
    <h1>Rechercher des étudiants</h1>

    <div class="search-bar">
        <form action="<%= request.getContextPath() %>/teacherList" method="get">
            <h1>Rechercher un étudiant</h1>
            <label for="teacherLastName">Nom de famille:</label>
            <input type="text" id="teacherLastName" name="teacherLastName" placeholder="Nom de famille">

            <label for="teacherFirstName">Prénom:</label>
            <input type="text" id="teacherFirstName" name="teacherFirstName" placeholder="Prénom">


            <button type="submit">Rechercher</button>
        </form>
    </div>

    <div class="teacher-results">
        <h2>Résultats de la recherche :</h2>
        <c:if test="${not empty teachers}">
            <table border="1">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="teacher" items="${teachers}">
                    <tr>
                        <td>${teacher.lastName}</td>
                        <td>${teacher.firstName}</td>
                        <td>
                            <form action="<%= request.getContextPath() %>/teacherList" method="post">
                                <input type="hidden" name="teacherId" value="${teacher.id}">
                                <button type="submit">Afficher les détails</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty teachers}">
            <p>Aucun enseignant trouvé.</p>
        </c:if>
    </div>

</div>

</body>
</html>
