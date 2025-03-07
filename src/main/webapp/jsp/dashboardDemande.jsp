<%@ page import="org.gestionobjets.models.Exchange" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css" />
</head>
<body>

<!-- Header -->
<header>
    <div class="navbar">
        <div class="logo">
            <a href="<%= request.getContextPath() %>/jsp/home.jsp">ÉCHANGE X OBJET</a>
        </div>
        <nav>
            <ul>
                <li><a href="<%= request.getContextPath() %>/jsp/objets.jsp">Objets</a></li>
                <li><a href="<%= request.getContextPath() %>/jsp/dashboardDemande.jsp">Échanges</a></li>
                <li><a href="<%= request.getContextPath() %>/logout">Déconnexion</a></li>
            </ul>
        </nav>
    </div>
</header>

<!-- Contenu du Dashboard -->
<div class="container">
    <h2>📊 Tableau de bord</h2>

    <!-- Demandes envoyées -->
    <section class="demandes-envoyees">
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
            <%-- Récupération des demandes envoyées depuis la session --%>
            <%
                List<Exchange> sentRequests = (List<Exchange>) session.getAttribute("sentRequests");
                if (sentRequests != null && !sentRequests.isEmpty()) {
                    for (Exchange exchange : sentRequests) {
            %>
            <tr>
                <td><%= exchange.getObjetDemande().getNom() %></td>
                <td><%= exchange.getObjetPropose().getProprietaire().getNom() %></td>
                <td><%= exchange.getObjetPropose().getNom() %></td>
                <td><%= exchange.getStatut() %></td>
                <td>
                    <button class="cancel">🗑 Annuler</button>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5">Aucune demande envoyée.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </section>

    <!-- Demandes reçues -->
    <section class="demandes-reçues">
        <h3>📥 Mes demandes reçues</h3>
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
            <%-- Récupération des demandes reçues depuis la session --%>
            <%
                List<Exchange> receivedRequests = (List<Exchange>) session.getAttribute("receivedRequests");
                if (receivedRequests != null && !receivedRequests.isEmpty()) {
                    for (Exchange exchange : receivedRequests) {
            %>
            <tr>
                <td><%= exchange.getObjetDemande().getNom() %></td>
                <td><%= exchange.getObjetPropose().getProprietaire().getNom() %></td>
                <td><%= exchange.getObjetPropose().getNom() %></td>
                <td><%= exchange.getStatut() %></td>
                <td>
                    <button class="accept">✅ Accepter</button>
                    <button class="decline">❌ Refuser</button>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5">Aucune demande reçue.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </section>

</div>

<script src="<%= request.getContextPath() %>/js/script.js"></script>

</body>
</html>
