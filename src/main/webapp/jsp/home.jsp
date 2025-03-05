<%--
  Created by IntelliJ IDEA.
  User: Dabo
  Date: 04/03/2025
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ObjectXChange - Accueil</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css" />
</head>
<body>
<div class="container">
    <h1>Bienvenue sur ObjectXChange</h1>
    <p>Échangez facilement des objets avec d'autres utilisateurs.</p>
    <div class="buttons">
        <a href="registration.jsp" class="btn">Créer un compte</a>
        <a href="login.jsp" class="btn btn-secondary">Se connecter</a>
        <a href="objets.jsp" class="btn btn-visitor">Continuer en tant que visiteur</a>
    </div>
</div>
</body>
</html>
