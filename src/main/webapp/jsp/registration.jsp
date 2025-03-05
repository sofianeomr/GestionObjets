<%--
  Created by IntelliJ IDEA.
  User: Dabo
  Date: 04/03/2025
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/registration.css" />
</head>
<body>
<div class="container">
    <h2>Créer un compte</h2>
    <form action="<%= request.getContextPath() %>/UserServlet?action=register" method="post">
        <label>Nom :</label>
        <input type="text" name="nom" required />

        <label>Email :</label>
        <input type="email" name="email" required />

        <label>Mot de passe :</label>
        <input type="password" name="motDePasse" required />

        <input type="submit" value="S'inscrire" />
    </form>
    <p>Déjà un compte ? <a href="login.jsp">Connectez-vous</a></p>
</div>
</body>
</html>
