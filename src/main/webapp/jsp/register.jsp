<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Inscription</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
</head>
<body>

<!-- Bouton retour -->
<button onclick="history.back()" class="back-button">← Retour</button>

<!-- Navbar -->
<header>
  <div class="navbar">
    <div class="logo">
      <a href="<%=request.getContextPath()%>/jsp/home.jsp">ÉCHANGE X OBJET</a>
    </div>
    <nav>
      <ul>
        <li><a href="<%=request.getContextPath()%>/jsp/home.jsp">Accueil</a></li>
        <li><a href="<%=request.getContextPath()%>/jsp/register.jsp" class="active">Inscription</a></li>
        <li><a href="<%=request.getContextPath()%>/jsp/login.jsp">Connexion</a></li>
      </ul>
    </nav>
  </div>
</header>

<!-- Formulaire d'inscription -->
<div class="form-container">
  <% String errorParam = request.getParameter("error"); %>
  <% if ("1".equals(errorParam)) { %>
  <p class="error-message">Cet email est déjà utilisé !</p>
  <% } %>

  <h2>Créer un compte</h2>
  <form action="<%= request.getContextPath() %>/UserServlet?action=register" method="post">
    <input type="hidden" name="action" value="register" />

    <label>Nom :</label>
    <input type="text" name="nom" required />

    <label>Email :</label>
    <input type="email" name="email" required />

    <label>Mot de passe :</label>
    <input type="password" name="motDePasse" required />

    <label>Confirmer le mot de passe :</label>
    <input type="password" name="confirmMotDePasse" required />

    <button type="submit">S'inscrire</button>
    <p>Déjà un compte ? <a href="<%=request.getContextPath()%>/jsp/login.jsp">Se connecter</a></p>
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
