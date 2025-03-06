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

    // Récupérer les objets de l'utilisateur
    ObjectDAO objectDAO = new ObjectDAO();
    List<Objet> userObjects = objectDAO.getObjectsByOwnerId(userId);

    // Récupérer tous les objets disponibles à l'échange, excepté ceux de l'utilisateur
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
                <li><a href="<%=request.getContextPath()%>/jsp/objets.jsp">Objets</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/dashboardDemande.jsp">Échanges</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/registration.jsp">Inscription</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/login.jsp">Connexion</a></li>
            </ul>
        </nav>
    </div>
</header>

<!-- Contenu principal -->
<div class="container">
    <button class="create-object-btn" onclick="openCreateObjectModal()">Créer un objet</button>

    <h2>Objets Disponibles</h2>
    <div class="objects-list">
        <% for (Objet objet : availableObjects) { %>
        <div class="object-item">
            <img src="<%=request.getContextPath()%>/images/<%= objet.getNom().toLowerCase() %>.jpg" alt="<%= objet.getNom() %>">
            <h3><%= objet.getNom() %></h3>
            <p>État : <%= objet.getDescription() %></p>
            <button onclick="openExchangeModal('<%= objet.getNom() %>', <%= objet.getId() %>)">Demander un échange</button>
        </div>
        <% } %>
    </div>
</div>

<!-- MODALE POUR CRÉATION D'OBJET -->
<div id="createObjectModal" class="modal" style="display: none;">
    <div class="modal-content">
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
                <option value="1">Électronique</option>
                <option value="2">Sport</option>
                <option value="3">Maison</option>
            </select>

            <button type="submit">Créer l'objet</button>
        </form>
    </div>
</div>
<!-- MODALE POUR DEMANDE D'ÉCHANGE -->
<div id="exchangeModal" class="modal" style="display: none;">
    <div class="modal-content">
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

<!-- Script JavaScript -->
<script>
    function openExchangeModal(objectName, objectId) {
        // Ouvrir la modal pour demander un échange
        document.getElementById('exchangeModal').style.display = 'block';

        // Afficher le nom de l'objet sélectionné dans la modal
        document.getElementById('selectedObject').innerText = objectName;

        // Ajouter l'ID de l'objet sélectionné dans un champ caché du formulaire
        document.getElementById('objectId').value = objectId;
    }

    function closeExchangeModal() {
        // Fermer la modal pour la demande d'échange
        document.getElementById('exchangeModal').style.display = 'none';
    }

    // Fonction pour mettre à jour le champ caché avec l'ID de l'objet sélectionné
    function updateSelectedObjectId() {
        var selectedObjectId = document.getElementById('myObjects').value;
        document.getElementById('selectedObjectId').value = selectedObjectId;
    }

    // Lorsque l'utilisateur clique à l'extérieur de la modal, fermer la modal
    window.onclick = function(event) {
        var modal = document.getElementById('exchangeModal');
        if (event.target == modal) {
            closeExchangeModal();
        }
    }
</script>


</body>
</html>
