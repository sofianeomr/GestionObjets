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
  <form action="<%=request.getContextPath()%>/jsp/register" method="post">
    <label>Nom :</label>
    <input type="text" name="name" required />

    <label>Email :</label>
    <input type="email" name="email" required />

    <label>Mot de passe :</label>
    <input type="password" name="password" required />

    <label>Confirmer le mot de passe :</label>
    <input type="password" name="confirm-password" required />

    <button type="submit">S'inscrire</button>
    <p>Déjà un compte ? <a href="<%=request.getContextPath()%>/jsp/login.jsp">Se connecter</a></p>
  </form>
</div>

</body>
</html>
