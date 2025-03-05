<%--
  Created by IntelliJ IDEA.
  User: sofia
  Date: 28/02/2025
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List, org.gestionobjets.models.Objet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Récupération de l'utilisateur connecté
    org.gestionobjets.models.Utilisateur utilisateur =
            (org.gestionobjets.models.Utilisateur) session.getAttribute("utilisateur");

    if (utilisateur == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Récupération de la liste des objets depuis la requête
    List<Objet> objets = (List<Objet>) request.getAttribute("objects");
%>

<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Bienvenue, <%= utilisateur.getNom() %> !</h1>

<h2>Vos objets</h2>
<ul>
    <% if (objets != null) {
        for (Objet objet : objets) { %>
    <li><%= objet.getNom() %> - <%= objet.getCategorie() %></li>
    <%  } } %>
</ul>

<h2>Ajouter un objet</h2>
<form action="HelloServlet?action=create-object" method="post">
    <label>Nom :</label>
    <input type="text" name="nom" required />

    <label>Catégorie :</label>
    <input type="text" name="categorie" required />

    <label>Description :</label>
    <input type="text" name="description" required />

    <input type="submit" value="Ajouter" />
</form>
</body>
</html>

