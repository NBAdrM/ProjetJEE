<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    // Vérifiez si une session existe et récupérez les attributs
    String role = (String) session.getAttribute("role");

    // Si aucun rôle n'est défini, redirigez vers la page de login
    if (role == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        return;
    }

    // Définir une classe CSS différente selon le rôle
    String roleClass = "student".equals(role) ? "background-student" : "background-teacher";
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Index <%= "student".equals(role) ? "Étudiant" : "Professeur" %></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/courseCalendar.css">
    <link href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.css' rel='stylesheet' />
    <link href='https://cdnjs.cloudflare.com/ajax/libs/qtip2/3.0.3/jquery.qtip.min.css' rel='stylesheet' />
    <script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.10.2/fullcalendar.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/qtip2/3.0.3/jquery.qtip.min.js'></script>
    <style>
        #calendar {
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
        }
    </style>
</head>
<body class="<%= roleClass %>">

<header>
    <nav>
        <div class="nav-center">
            <a href="<%= request.getContextPath() %>/home.jsp">Accueil</a>

            <% if ("student".equals(role)) { %>
            <a href="<%= request.getContextPath() %>/ListInscriptionServlet">Inscriptions Cours</a>
            <% } else if ("teacher".equals(role)) { %>
            <a href="<%= request.getContextPath() %>/course/courseServlet.jsp">Création Cours</a>
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
    <h1>Bienvenue sur votre Espace <%= role.equals("student") ? "Étudiant" : "Professeur" %></h1>

    <section class="emploi-du-temps">
        <h2>Votre Emploi du Temps</h2>
        <div id='calendar'></div>
    </section>
</div>

<script>
    $(document).ready(function() {
        var events = [
            <c:forEach var="course" items="${listCourses}">
            {
                title: "${course.name} \n Professeur: ${course.teacherName} \n Salle: ${course.classroom}",
                start: '${course.dateCourse.date}T${course.dateCourse.startTime}',  // Date et heure de début
                end: '${course.dateCourse.date}T${course.dateCourse.endTime}',      // Date et heure de fin
                description: 'Enseignant: ${course.teacherName}, Salle: ${course.classroom}' // Description complète
            },
            </c:forEach>
        ];

        // Vérification dans la console pour debug
        <c:forEach var="course" items="${listCourses}">
        console.log("Date: ${course.dateCourse.date}");
        console.log("Heure début: ${course.dateCourse.startTime}");
        console.log("Heure fin: ${course.dateCourse.endTime}");
        </c:forEach>;

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultView: 'agendaWeek',
            editable: false,
            events: events,
            minTime: "08:00:00",
            maxTime: "20:00:00",
            validRange: {
                start: '2024-01-01',
                end: '2024-12-31'
            },
            weekends: false,
            eventRender: function(event, element) {
                element.qtip({
                    content: event.description
                });
            }
        });
    });
</script>

</body>
</html>
