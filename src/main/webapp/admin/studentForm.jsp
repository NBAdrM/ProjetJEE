<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Student Form</title>
</head>
<body>
<h1>Student Form</h1>
<form action="StudentServlet" method="post">
  <input type="hidden" name="id" value="${id}">
  <label>Nom:</label>
  <input type="text" name="name" required><br>
  <label>Age:</label>
  <input type="number" name="age" required><br>
  <label>Classe:</label>
  <input type="text" name="class" required><br>
  <input type="submit" value="Soumettre">
</form>
</body>
</html>