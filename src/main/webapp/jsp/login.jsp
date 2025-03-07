<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Se connecter</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
</head>
<body>

<!-- Bouton retour -->
<button onclick="history.back()" class="back-button">← Retour</button>

<!-- Formulaire de connexion -->
<div class="form-container">
    <% String errorParam = request.getParameter("error"); %>
    <% if ("1".equals(errorParam)) { %>
    <p class="error-message">Identifiants invalides !</p>
    <% } %>

    <h2>Connexion</h2>
    <form action="<%= request.getContextPath() %>/UserServlet?action=login" method="post">
        <label>Email :</label>
        <input type="email" name="email" required />

        <label>Mot de passe :</label>
        <input type="password" name="motDePasse" required />

        <button type="submit">Se connecter</button>
        <p>Pas encore inscrit ? <a href="<%=request.getContextPath()%>/jsp/register.jsp">Créer un compte</a></p>
    </form>
</div>

<!-- Style CSS pour le bouton retour -->
<style>
    .back-button {
        background-color: #f1f1f1;
        border: none;
        padding: 10px 15px;
        font-size: 16px;
        cursor: pointer;
        position: absolute;
        top: 10px;
        left: 10px;
        border-radius: 5px;
    }

    .back-button:hover {
        background-color: #ddd;
    }
</style>

</body>
</html>
