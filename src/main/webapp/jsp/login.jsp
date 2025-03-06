<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <!-- Lien vers le CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
</head>
<body>

<div class="form-container">
    <%
        // Si on reçoit un paramètre error=1, on affiche un message d'erreur
        String errorParam = request.getParameter("error");
        if ("1".equals(errorParam)) {
    %>
    <p class="error-message">Identifiants invalides !</p>
    <%
        }
    %>

    <h2>Login</h2>
    <form action="<%= request.getContextPath() %>/user-servlet?action=login" method="post">
        <label>Email :</label>
        <input type="email" name="email" required />

        <label>Mot de passe :</label>
        <input type="password" name="motDePasse" required />

        <button type="submit">Se connecter</button>
    </form>

</div>

</body>
</html>
