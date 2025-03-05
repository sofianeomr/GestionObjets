<%--
  Created by IntelliJ IDEA.
  User: Dabo
  Date: 04/03/2025
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Objets</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/objets.css" />
</head>
<body>
<div class="container">
    <h2>Objets Disponibles</h2>
    <div class="objects-list">
        <div class="object-item">
            <h3>Vélo de route</h3>
            <p>État : Très bon</p>
            <button onclick="redirectToLogin()">Demander un échange</button>
        </div>
        <div class="object-item">
            <h3>Ordinateur portable</h3>
            <p>État : Bon</p>
            <button onclick="redirectToLogin()">Demander un échange</button>
        </div>
    </div>
    <a href="home.jsp" class="btn btn-back">Retour à l'accueil</a>
</div>

<script>
    function redirectToLogin() {
        alert("Vous devez créer un compte pour échanger des objets.");
        window.location.href = "registration.jsp";
    }
</script>
</body>
</html>
