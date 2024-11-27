<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des étudiants</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/courseCalendar.css">
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
        <form action="<%= request.getContextPath() %>/studentList" method="get">
            <h1>Rechercher un étudiant</h1>
            <label for="studentLastName">Nom de famille:</label>
             <input type="text" id="studentLastName" name="studentLastName" placeholder="Nom de famille">

            <label for="studentFirstName">Prénom:</label>
            <input type="text" id="studentFirstName" name="studentFirstName" placeholder="Prénom">


            <button type="submit">Rechercher</button>
        </form>
    </div>

    <div class="student-results">
        <h2>Résultats de la recherche :</h2>
        <c:if test="${not empty students}">
            <table border="1">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.lastName}</td>
                        <td>${student.firstName}</td>
                        <td>
                            <form action="<%= request.getContextPath() %>/studentList" method="details">
                                <input type="hidden" name="studentId" value="${student.id}">
                                <button type="submit">Afficher les détails</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty students}">
            <p>Aucun étudiant trouvé.</p>
        </c:if>
    </div>

</div>

</body>
</html>
