<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administration Panel</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/admin.css">
    <style>
        .a {
            position: fixed;
            top: 10px;
            left: 10px;
            font-weight: bold;
            color: #333333;
            text-decoration: none;
            background-color: #ffffff;
            padding: 5px 10px;
            border: 1px solid #333333;
            border-radius: 4px;
            z-index: 1000;
        }
        .a:hover {
            background-color: #5982C2;
            color: #ffffff;
        }
    </style>
</head>
<body>
<h1>Administration Panel</h1>

<!-- Affichage des messages de réussite et d'erreur -->
<c:if test="${not empty success}">
    <p style="color: green;">${success}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<!-- Formulaire pour l'action de l'administrateur -->
<form action="${pageContext.request.contextPath}/adminServlet" method="post">
    <label for="action">Action:</label>
    <select name="action" id="action" required>
        <option value="create">Créer</option>
        <option value="modify">Modifier</option>
        <option value="delete">Effacer</option>
    </select>
    <br><br>

    <label for="role">Role:</label>
    <select name="role" id="role" required>
        <option value="student">Elève</option>
        <option value="teacher">Professeur</option>
    </select>
    <br><br>

    <!-- Champ pour l'ID, requis seulement pour modifier ou effacer -->
    <div id="id-container" style="display: none;">
        <label for="id">ID (pour modifier/effacer):</label>
        <input type="text" name="id" id="id">
        <br><br>
    </div>

    <input type="submit" value="Soumettre">
</form>

<!-- Bouton de retour à l'accueil -->
<a href="home.jsp" class="a">Retour à l'accueil</a>

<!-- Script pour montrer ou cacher le champ ID selon l'action choisie -->
<script>
    document.getElementById('action').addEventListener('change', function () {
        var idContainer = document.getElementById('id-container');
        var idField = document.getElementById('id');
        if (this.value === 'modify' || this.value === 'delete') {
            idContainer.style.display = 'block';
            idField.required = true;
        } else {
            idContainer.style.display = 'none';
            idField.required = false;
            idField.value = '';
        }
    });
</script>

</body>
</html>
