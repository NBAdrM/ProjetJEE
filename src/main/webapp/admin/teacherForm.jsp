<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Teacher Form</title>
</head>
<body>
<h1>Teacher Form</h1>
<form action="TeacherServlet" method="post">
  <input type="hidden" name="id" value="${id}">
  <label>Nom:</label>
  <input type="text" name="name" required><br>
  <label>Matière:</label>
  <input type="text" name="subject" required><br>
  <label>Experience (années):</label>
  <input type="number" name="experience" required><br>
  <input type="submit" value="Soumettre">
</form>
</body>
</html>