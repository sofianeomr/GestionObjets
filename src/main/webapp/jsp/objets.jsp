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
        <div class="object-item">
            <img src="<%=request.getContextPath()%>/images/velo.jpg" alt="Vélo de route">
            <h3>Vélo de route</h3>
            <p>État : Très bon</p>
            <button onclick="openExchangeModal('Vélo de route')">Demander un échange</button>
        </div>
        <div class="object-item">
            <img src="<%=request.getContextPath()%>/images/ordinateur.jpg" alt="Ordinateur portable">
            <h3>Ordinateur portable</h3>
            <p>État : Bon</p>
            <button onclick="openExchangeModal('Ordinateur portable')">Demander un échange</button>
        </div>
    </div>
</div>

<!-- MODALE POUR CRÉATION D'OBJET -->
<div id="createObjectModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeCreateObjectModal()">&times;</span>
        <h2>Créer un nouvel objet</h2>

        <form action="<%=request.getContextPath()%>/createObjectServlet" method="post">
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
<div id="exchangeModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeExchangeModal()">&times;</span>
        <h2>Demande d'échange</h2>
        <p>Vous souhaitez échanger contre : <strong id="selectedObject"></strong></p>

        <label for="myObjects">Choisissez un de vos objets :</label>
        <select id="myObjects">
            <option value="">Sélectionner un objet...</option>
            <option value="Livre">Livre</option>
            <option value="Casque audio">Casque audio</option>
            <option value="Sac à dos">Sac à dos</option>
        </select>

        <button onclick="submitExchangeRequest()">Envoyer la demande</button>
    </div>
</div>

<!-- Script JavaScript -->
<script src="<%=request.getContextPath()%>/js/script.js"></script>

</body>
</html>
