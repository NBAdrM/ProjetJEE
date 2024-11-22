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
        <option> </option>
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
    <!-- Les lignes seront ajoutées dynamiquement ici -->
    </tbody>
</table>

<!-- Script pour le filtre -->
<script>
    function filterTable() {
        let filter = document.getElementById("filter").value;
        let contextPath = '${pageContext.request.contextPath}';

        console.log("Rôle sélectionné : ", filter);

        fetch(contextPath + "/adminServlet?role=" + filter)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erreur lors de la récupération des données");
                }
                return response.json();
            })
            .then(data => {
                // Réinitialiser le tableau
                var tableBody = document.querySelector("#userTable tbody");
                tableBody.innerHTML = "";

                // Ajouter les nouvelles lignes
                data.forEach(user => {
                    var row = document.createElement("tr");
                    row.setAttribute("data-role", filter);

                    var lastNameCell = document.createElement("td");
                    lastNameCell.textContent = user.lastName;

                    var firstNameCell = document.createElement("td");
                    firstNameCell.textContent = user.firstName;

                    var roleCell = document.createElement("td");
                    roleCell.textContent = filter === "student" ? "Élève" : "Professeur";

                    console.log("Context", contextPath);
                    console.log("filter", filter);
                    console.log("user.id", user.id);

                    var actionsCell = document.createElement("td");
                    actionsCell.innerHTML = `
                    <a href="${contextPath}/adminServlet?action=modify&role=${filter}&id=${user.id}" class="button">Modifier</a>
                    <a href="${contextPath}/adminServlet?action=delete&role=${filter}&id=${user.id}" class="button delete"
                    onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');">
                    Supprimer </a>
                `;

                    row.appendChild(lastNameCell);
                    row.appendChild(firstNameCell);
                    row.appendChild(roleCell);
                    row.appendChild(actionsCell);

                    tableBody.appendChild(row);
                });
            })
            .catch(error => {
                console.error("Erreur:", error);
            });
    }
</script>
</body>
</html>
