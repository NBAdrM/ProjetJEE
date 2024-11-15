<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Administration Panel</title>
</head>
<body>
<h1>Administration Panel</h1>
<c:if test="${not empty success}">
    <p style="color: green;">${success}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>
<form action="AdminServlet" method="post">
    <label>Action:</label>
    <select name="action">
        <option value="créer">Créer</option>
        <option value="modifier">Modifier</option>
        <option value="effacer">Effacer</option>
    </select>
    <label>Role:</label>
    <select name="role">
        <option value="élève">Elève</option>
        <option value="professeur">Professeur</option>
    </select>
    <label>ID (pour modifier/effacer):</label>
    <input type="text" name="id">
    <input type="submit" value="Soumettre">
</form>
</body>
</html>