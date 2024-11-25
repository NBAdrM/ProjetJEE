<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Inscription à un Cours</title>
  <script>
    const contextPath = "${pageContext.request.contextPath}";
  </script>
  <script src="${pageContext.request.contextPath}/resources/js/initCourse.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/enrollment.css">
</head>
<body>
<h1>Inscription à un Cours</h1>

<div id="messageContainer" style="display: none;"></div>

<form id="enrollForm" method="post" action="${pageContext.request.contextPath}/enrollment">
  <label for="courseId">Choisissez un cours :</label>
  <select id="courseId" name="courseId" required>
    <option value="">-- Sélectionnez un cours --</option>
  </select><br>

  <button type="submit">Inscrire</button>
</form>

<a href="${pageContext.request.contextPath}/home.jsp">
  <button>Revenir à l'accueil</button>
</a>

</body>
</html>
