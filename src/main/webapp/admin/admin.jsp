<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.models.Student" %>
<%@ page import="com.example.projetjee.models.Teacher" %>
<!DOCTYPE html>
<html>
<head>
    <title>Panneau Administration</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/admin.css">
</head>
<body>
<h1>Panneau d'administration</h1>

<!-- Boutons pour ajouter un nouvel utilisateur -->
<div style="text-align: center; margin-bottom: 20px;">
    <a href="${pageContext.request.contextPath}/admin/studentForm.jsp" class="button">Ajouter un élève</a>
    <a href="${pageContext.request.contextPath}/admin/teacherForm.jsp" class="button">Ajouter un professeur</a>
</div>

<!-- Filtre pour afficher les élèves ou les professeurs -->
<div class="filter-container" style="margin-bottom: 20px;">
    <label for="filter">Filtrer par rôle : </label>
    <select id="filter" onchange="filterTable()">
        <option value="all">Tous</option>
        <option value="student">Élèves</option>
        <option value="teacher">Professeurs</option>
    </select>
</div>

<!-- Tableau des utilisateurs -->
<table id="userTable" border="1" cellspacing="0" cellpadding="10" style="width: 100%; text-align: left;">
    <thead>
    <tr>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Rôle</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        // Récupérer les listes d'étudiants et de professeurs depuis l'attribut de requête
        List<Student> students = (List<Student>) request.getAttribute("list");
        List<Teacher> teachers = (List<Teacher>) request.getAttribute("list");

        // Si la liste d'étudiants est définie, on l'affiche
        if (students != null) {
            for (Student student : students) {
    %>
    <tr data-role="student">
        <td><%= student.getLastName() %></td>
        <td><%= student.getFirstName() %></td>
        <td>Élève</td>
        <td>
            <a href="<%= request.getContextPath() %>/adminServlet?action=modify&role=student&id=<%= student.getId() %>" class="button">
                Modifier
            </a>
            <a href="<%= request.getContextPath() %>/adminServlet?action=delete&role=student&id=<%= student.getId() %>"
               class="button delete"
               onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');">
                Supprimer
            </a>
        </td>
    </tr>
    <%
            }
        }

        // Si la liste de professeurs est définie, on l'affiche
        if (teachers != null) {
            for (Teacher teacher : teachers) {
    %>
    <tr data-role="teacher">
        <td><%= teacher.getLastName() %></td>
        <td><%= teacher.getFirstName() %></td>
        <td>Professeur</td>
        <td>
            <a href="<%= request.getContextPath() %>/adminServlet?action=modify&role=teacher&id=<%= teacher.getId() %>" class="button">
                Modifier
            </a>
            <a href="<%= request.getContextPath() %>/adminServlet?action=delete&role=teacher&id=<%= teacher.getId() %>"
               class="button delete"
               onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');">
                Supprimer
            </a>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<!-- Script pour le filtre -->
<script>
    function filterTable() {
        var filter = document.getElementById("filter").value;
        var rows = document.querySelectorAll("#userTable tbody tr");

        rows.forEach(row => {
            if (filter === "all" || row.getAttribute("data-role") === filter) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }
</script>
</body>
</html>
