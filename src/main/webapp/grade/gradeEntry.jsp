<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    // Vérifier si la session est valide et obtenir le rôle de l'utilisateur
    String role = (String) session.getAttribute("role");
    if (role == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        return;
    } else if (!"teacher".equals(role)) {
        response.sendRedirect(request.getContextPath() + "/home.jsp");
        return;
    }
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
    <div>
        <label for="studentId">ID de l'Étudiant:</label>
        <input type="text" id="studentId" name="studentId" required>
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