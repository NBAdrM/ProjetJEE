<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    // Vérifier si la session est valide et obtenir le rôle de l'utilisateur
    String role = (String) session.getAttribute("role");
//    if (role == null) {
//        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
//        return;
//    } else if (!"teacher".equals(role)) {
//        response.sendRedirect(request.getContextPath() + "/home.jsp");
//        return;
//    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrement des Notes</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/courseCalendar.css">
</head>
<body>

<header>
    <nav>
        <div class="nav-center">
            <a href="<%= request.getContextPath() %>/home.jsp">Accueil</a>
            <% if ("teacher".equals(role)) { %>
            <a href="<%= request.getContextPath() %>/course/courseCalendar.jsp">Emploi du temps</a>
            <% } %>
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
    <h1>Enregistrer les Notes</h1>

    <div class="search-bar">
        <form action="<%= request.getContextPath() %>/grade" method="get">
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
                            <form action="<%= request.getContextPath() %>/grade" method="post">
                                <input type="hidden" name="studentId" value="${student.id}">
                                <button type="submit">Sélectionner</button>
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

    <div>
        <label for="courseId">ID du Cours:</label>
        <input type="text" id="courseId" name="courseId" required>
    </div>

    <div>
        <label for="courseName">Nom du Cours:</label>
        <input type="text" id="courseName" name="courseName" required>
    </div>

    <div>
        <label for="grade">Note:</label>
        <input type="number" id="grade" name="grade" min="0" max="20" step="0.1" required>
    </div>

    <button type="submit">Enregistrer</button>
    </form>
</div>

</body>
</html>
