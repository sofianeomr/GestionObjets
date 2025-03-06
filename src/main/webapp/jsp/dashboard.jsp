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

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }

        h1, h2 {
            text-align: center;
            color: #333;
        }

        .container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
        }

        .card {
            background-color: white;
            border-radius: 8px;
            width: 300px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .card:hover {
            transform: translateY(-10px);
        }

        .card h3 {
            font-size: 1.5em;
            margin-bottom: 10px;
            color: #4CAF50;
        }

        .card p {
            font-size: 1em;
            color: #555;
        }

        .btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-top: 10px;
            font-size: 1em;
        }

        .btn:hover {
            background-color: #45a049;
        }

        form {
            text-align: center;
            margin-top: 30px;
        }

        input[type="text"] {
            padding: 10px;
            width: 200px;
            margin: 10px 0;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

    </style>
</head>
<body>

<h1>Bienvenue, <%= utilisateur.getNom() %> !</h1>

<h2>Voici vos objets:</h2>

<div class="container">
    <% if (objets != null) {
        for (Objet objet : objets) { %>
    <div class="card">
        <h3><%= objet.getNom() %></h3>
        <p><strong>Catégorie:</strong> <%= objet.getCategorie() %></p>
        <p><strong>Description:</strong> <%= objet.getDescription() %></p>
        <a href="#" class="btn">Demander Échange</a>
    </div>
    <%  } } %>
</div>

<h2>Ajouter un objet</h2>
<form action="<%= request.getContextPath() %>/user-servlet?action=create-object" method="post">
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
