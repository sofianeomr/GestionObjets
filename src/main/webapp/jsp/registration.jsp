<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/registration.css" />
</head>
<body>

<!-- Bouton Retour -->
<button onclick="history.back()" class="back-button">← Retour</button>

<div class="container">
    <h2>Créer un compte</h2>
    <form action="<%= request.getContextPath() %>/UserServlet?action=register" method="post">
        <label>Nom :</label>
        <input type="text" name="nom" required />

        <label>Email :</label>
        <input type="email" name="email" required />

        <label>Mot de passe :</label>
        <input type="password" name="motDePasse" required />

        <Button type="submit" >S'inscrire</Button>
    </form>
    <p>Déjà un compte ? <a href="login.jsp">Connectez-vous</a></p>
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
