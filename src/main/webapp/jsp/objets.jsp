<%@ page import="org.gestionobjets.models.Objet" %>
<%@ page import="java.util.List" %>
<%@ page import="org.gestionobjets.dao.ObjectDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Objets Disponibles</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/objets.css" />
</head>
<body>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        userId = -1;
    }

    ObjectDAO objectDAO = new ObjectDAO();
    List<Objet> userObjects = (userId != -1) ? objectDAO.getObjectsByOwnerId(userId) : null;
    List<Objet> availableObjects = objectDAO.getAllObjetsExceptOwner(userId);
%>

<p>ID Utilisateur : <%= userId %></p>

<!-- Navbar -->
<header>
    <div class="navbar">
        <div class="logo">
            <a href="<%=request.getContextPath()%>/jsp/home.jsp">ÉCHANGE X OBJET</a>
        </div>
        <nav>
            <ul>
                <% if (userId != -1) { %>
                <li><a href="<%=request.getContextPath()%>/jsp/objets.jsp">Objets</a></li>
                <li><a href="<%=request.getContextPath()%>/UserServlet?action=sent-requests">Échanges</a></li>
                <li><a href="<%=request.getContextPath()%>/logout">Déconnexion</a></li>
                <% } %>
                <% if (userId == -1) { %>
                <li><a href="<%=request.getContextPath()%>/jsp/registration.jsp">Inscription</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/login.jsp">Connexion</a></li>
                <% } %>
            </ul>
        </nav>
    </div>
</header>

<!-- Contenu principal -->
<div class="container">
    <% if (userId != -1) { %>
    <button class="create-object-btn" onclick="openCreateObjectModal()">Créer un objet</button>
    <% } %>

    <h2>Objets Disponibles</h2>
    <div class="objects-list">
        <% for (Objet objet : availableObjects) { %>
        <div class="object-item">
            <img src="<%=request.getContextPath()%>/images/<%= objet.getNom().toLowerCase() %>.jpg" alt="<%= objet.getNom() %>">
            <h3><%= objet.getNom() %></h3>
            <p>État : <%= objet.getDescription() %></p>
            <% if (userId != -1) { %>
            <button onclick="openExchangeModal('<%= objet.getNom() %>', <%= objet.getId() %>)">Demander un échange</button>
            <% } %>
        </div>
        <% } %>
    </div>
</div>

<!-- MODALE POUR CRÉATION D'OBJET -->
<% if (userId != -1) { %>
<div id="createObjectModal" class="modal">
    <div class="modal-content" onclick="event.stopPropagation()">
        <span class="close" onclick="closeCreateObjectModal()">&times;</span>
        <h2>Créer un nouvel objet</h2>

        <form action="<%=request.getContextPath()%>/UserServlet?action=create-object" method="post">
            <label for="objectName">Nom de l'objet :</label>
            <input type="text" id="objectName" name="nom" required>

            <label for="objectDescription">Description :</label>
            <textarea id="objectDescription" name="description" required></textarea>

            <label for="objectCategory">Catégorie :</label>
            <select id="objectCategory" name="categorie_id" required>
                <option value="">Sélectionner une catégorie...</option>
                <option value="3">Électronique</option>
                <option value="4">Sport</option>
                <option value="5">Livre</option>
                <option value="6">Cuisine</option>
                <option value="7">Automobile</option>
            </select>

            <button type="submit">Créer l'objet</button>
        </form>
    </div>
</div>
<% } %>

<!-- MODALE POUR DEMANDE D'ÉCHANGE -->
<% if (userId != -1) { %>
<div id="exchangeModal" class="modal">
    <div class="modal-content" onclick="event.stopPropagation()">
        <span class="close" onclick="closeExchangeModal()">&times;</span>
        <h2>Demande d'échange</h2>
        <p>Vous souhaitez échanger contre : <strong id="selectedObject"></strong></p>

        <label for="myObjects">Choisissez un de vos objets :</label>
        <select id="myObjects" onchange="updateSelectedObjectId()">
            <option value="">Sélectionner un objet...</option>
            <% for (Objet userObjet : userObjects) { %>
            <option value="<%= userObjet.getId() %>"><%= userObjet.getNom() %></option>
            <% } %>
        </select>

        <form id="exchangeForm" action="<%=request.getContextPath()%>/UserServlet?action=request-exchange" method="post">
            <input type="hidden" name="objectId" id="objectId" value="">
            <input type="hidden" name="selectedObjectId" id="selectedObjectId" value="">
            <input type="hidden" name="ownerId" value="<%= userId %>">
            <button type="submit">Envoyer la demande</button>
        </form>
    </div>
</div>
<% } %>

<!-- Script JavaScript -->
<script>
    function openCreateObjectModal() {
        document.getElementById("createObjectModal").style.display = "flex";
    }

    function closeCreateObjectModal() {
        document.getElementById("createObjectModal").style.display = "none";
    }

    function openExchangeModal(objectName, objectId) {
        document.getElementById('exchangeModal').style.display = 'flex';
        document.getElementById('selectedObject').innerText = objectName;
        document.getElementById('objectId').value = objectId;
    }

    function closeExchangeModal() {
        document.getElementById('exchangeModal').style.display = 'none';
    }

    function updateSelectedObjectId() {
        var selectedObjectId = document.getElementById('myObjects').value;
        document.getElementById('selectedObjectId').value = selectedObjectId;
    }

    // Fermer la pop-up si on clique à l'extérieur
    window.onclick = function(event) {
        let createObjectModal = document.getElementById("createObjectModal");
        let exchangeModal = document.getElementById("exchangeModal");

        if (event.target === createObjectModal) {
            createObjectModal.style.display = "none";
        }

        if (event.target === exchangeModal) {
            exchangeModal.style.display = "none";
        }
    };
</script>

</body>
</html>
