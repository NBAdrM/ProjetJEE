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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/courseCalendar.css">
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
    <h1>Liste des notes</h1>

    <div class="grade-results">
        <c:if test="${not empty grades}">
            <table border="1">
                <thead>
                <tr>
                    <th>Matière</th>
                    <th>Note</th>
                    <th>Random</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.lastName}</td>
                        <td>${student.firstName}</td>
                        <td>
                            <form action="<%= request.getContextPath() %>/studentList" method="post">
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
