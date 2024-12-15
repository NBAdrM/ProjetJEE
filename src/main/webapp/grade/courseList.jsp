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
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrement des Notes</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/courseCalendar.css">
    <script src="${pageContext.request.contextPath}/resources/js/initCourseByStudent.js "></script>
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
    <h1>Liste des cours</h1>
    <form action="<%= request.getContextPath() %>/gradeCourseList" method="post">
        <label for="course">Choisissez le cours dont vous voulez les notes:</label>
        <select id="course" name="courseId">
            <c:forEach var="course" items="${courses}">
                <option value="${course.id}">${course.name} - ${course.year} - ${course.classroom}</option>
            </c:forEach>
        </select>
        <br>


        <button type="submit">Consulter les notes</button>
    </form>

    <h1>Télécharger votre bulletin de note</h1>
    <form action="<%= request.getContextPath() %>/downloadBulletin" method="get">
        <input type="text" id="studentId" name="studentId" hidden="true" value="<%= username %>">

        <label for="year">Entrez l'année scolaire:</label>
        <select id="year" name="year">
            <option value="2024">2024/2025</option>
        </select>
        <button type="submit">Télécharger le bulletin</button>
    </form>

</div>

</body>
</html>