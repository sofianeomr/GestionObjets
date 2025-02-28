<%--
  Created by IntelliJ IDEA.
  User: sofia
  Date: 28/02/2025
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label>Email :</label>
    <input type="text" name="email" required />
    <br/>
    <label>Mot de passe :</label>
    <input type="password" name="password" required />
    <br/>
    <input type="submit" value="Se connecter" />
</form>
</body>
</html>

