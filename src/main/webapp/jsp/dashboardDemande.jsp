<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/dashboard.css" />
</head>
<body>

<!-- Header -->
<header>
    <div class="navbar">
        <div class="logo">
            <a href="<%=request.getContextPath()%>/jsp/home.jsp">ÉCHANGE X OBJET</a>
        </div>
        <nav>
            <ul>
                <li><a href="<%=request.getContextPath()%>/jsp/objets.jsp">Objets</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/dashboardDemande.jsp">Échanges</a></li>
                <li><a href="<%=request.getContextPath()%>/logout">Déconnexion</a></li>
            </ul>
        </nav>
    </div>
</header>

<!-- Contenu du Dashboard -->
<div class="container">
    <h2>📊 Tableau de bord</h2>

    <!-- Demandes reçues -->
    <section class="demandes">
        <h3>📥 Demandes reçues</h3>
        <table>
            <thead>
            <tr>
                <th>Objet demandé</th>
                <th>Demandeur</th>
                <th>Objet proposé</th>
                <th>Statut</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Vélo de route</td>
                <td>Jean Dupont</td>
                <td>Ordinateur portable</td>
                <td>En attente</td>
                <td>
                    <button class="accept">✅ Accepter</button>
                    <button class="reject">❌ Refuser</button>
                </td>
            </tr>
            </tbody>
        </table>
    </section>

    <!-- Mes demandes envoyées -->
    <section class="demandes">
        <h3>📤 Mes demandes envoyées</h3>
        <table>
            <thead>
            <tr>
                <th>Objet demandé</th>
                <th>Propriétaire</th>
                <th>Objet proposé</th>
                <th>Statut</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <!-- Boucle JSP pour afficher les demandes envoyées -->
            <c:forEach var="exchange" items="${sentRequests}">
                <tr>
                    <td>${exchange.objetDemande.nom}</td>
                    <td>${exchange.objetPropose.proprietaire.nom}</td>
                    <td>${exchange.objetPropose.nom}</td>
                    <td>${exchange.statut}</td>
                    <td>
                        <button class="cancel">🗑 Annuler</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>

<script src="<%= request.getContextPath() %>/js/script.js"></script>

</body>
</html>
