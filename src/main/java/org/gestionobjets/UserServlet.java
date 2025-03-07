package org.gestionobjets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.gestionobjets.dao.UserDAO;
import org.gestionobjets.dao.ObjectDAO;
import org.gestionobjets.dao.ExchangeDAO;
import org.gestionobjets.models.Categorie;
import org.gestionobjets.models.Exchange;
import org.gestionobjets.models.Utilisateur;
import org.gestionobjets.models.Objet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;
    private ObjectDAO objectDAO;
    private ExchangeDAO exchangeDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        objectDAO = new ObjectDAO();
        exchangeDAO = new ExchangeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "register":
                registerUser(request, response);
                break;
            case "login":
                loginUser(request, response);
                break;
            case "create-object":
                createObject(request, response);
                break;
            case "request-exchange":
                requestExchange(request, response);
                break;
            case "manage-exchange":
                manageExchange(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer l'action de la requête
        String action = request.getParameter("action");
        switch (action) {
            case "list-objects":
                listObjects(request, response);
                break;
            case "search":
                searchObjects(request, response);
                break;
            case "history":
                showExchangeHistory(request, response);
                break;
            case "sent-requests":
                showSentRequests(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    private void showSentRequests(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialisation de la session
        HttpSession session = request.getSession(true); // true crée une nouvelle session si elle n'existe pas encore

        // Récupérer l'ID utilisateur de la session
        Integer userId = (Integer) session.getAttribute("userId");

        // Vérification si l'utilisateur est connecté
        if (userId == null) {
            System.out.println("❌ [ERROR] Utilisateur non connecté - Redirection vers login.jsp");
            // Redirection vers la page de login si l'utilisateur n'est pas connecté
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("🔍 [DEBUG] Utilisateur ID récupéré : " + userId);

        // Création d'une instance de ExchangeDAO pour récupérer les échanges envoyés
        ExchangeDAO exchangeDAO = new ExchangeDAO();

        // Récupération des demandes envoyées depuis la base de données
        List<Exchange> sentRequests = exchangeDAO.getSentRequestsByUserId(userId);

        // Si aucune demande envoyée n'est trouvée
        if (sentRequests == null || sentRequests.isEmpty()) {
            System.out.println("❌ [ERROR] Aucune demande envoyée trouvée pour l'utilisateur ID: " + userId);

            // Si aucune demande n'est trouvée, nous utilisons une liste vide
            sentRequests = new ArrayList<>();
        } else {
            System.out.println("✅ [SUCCESS] Nombre de demandes envoyées trouvées : " + sentRequests.size());

            // Affichage de toutes les demandes envoyées pour le debug
            for (Exchange exchange : sentRequests) {
                System.out.println("📦 [DEBUG] Demande envoyée : " + exchange);
            }
        }

        // Vérification que les demandes envoyées sont stockées correctement dans la session
        if (sentRequests != null) {
            System.out.println("✅ [DEBUG] Stockage des demandes envoyées dans la session");
        } else {
            System.out.println("❌ [ERROR] Erreur lors du stockage des demandes dans la session");
        }

        // Stockage des données dans la session pour éviter la perte après un rafraîchissement
        session.setAttribute("sentRequests", sentRequests);


        // Redirection vers la page JSP pour afficher les demandes envoyées
        request.getRequestDispatcher("/jsp/dashboardDemande.jsp").forward(request, response);
    }

    /*** 1. Création de compte ***/
    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Utilisateur utilisateur = new Utilisateur(nom, email, motDePasse);
        boolean success = userDAO.registerUser(utilisateur);

        if (success) {
            response.sendRedirect("jsp/login.jsp");
        } else {
            request.setAttribute("errorMessage", "Erreur lors de l'inscription.");
            request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Utilisateur utilisateur = userDAO.connexionUser(email, motDePasse);

        if (utilisateur != null) {
            // Récupérer l'ID utilisateur en base à partir de l'email
            int userId = userDAO.getUserIdByEmail(email);

            // Stocker l'ID et l'email en session
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("userEmail", utilisateur.getEmail());

            // Redirection vers le tableau de bord après connexion
            response.sendRedirect("jsp/objets.jsp");
        } else {
            request.setAttribute("errorMessage", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }



    /*** 3. Création d'un objet ***/
    /*** 3. Création d'un objet ***/
    private void createObject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Récupération correcte de l'ID utilisateur

        if (userId == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        // Récupération des paramètres du formulaire
        String nom = request.getParameter("nom");
        int categorieId = Integer.parseInt(request.getParameter("categorie_id"));
        String description = request.getParameter("description");

        // Création de l'objet avec l'ID utilisateur
        Utilisateur proprietaire = new Utilisateur();
        proprietaire.setId(userId); // Assigner seulement l'ID

        Objet objet = new Objet(nom, description, new Categorie(categorieId), proprietaire);
        objectDAO.addObject(objet);

        // Redirection après l'ajout
        List<Objet> objets = objectDAO.getAllObjets();
        request.setAttribute("objects", objets);
        request.getRequestDispatcher("jsp/objets.jsp").forward(request, response);
    }


    /*** 4. Liste des objets ***/
    private void listObjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer l'ID de l'utilisateur depuis la session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        // Récupérer les objets de l'utilisateur et les objets disponibles (pas de son propriétaire)
        List<Objet> objetsUtilisateur = objectDAO.getObjectsByOwnerId(userId);
        List<Objet> objetsDisponibles = objectDAO.getAllObjetsExceptOwner(userId);

        // Passer les objets à la JSP
        request.setAttribute("mesObjets", objetsUtilisateur); // Objets de l'utilisateur
        request.setAttribute("objetsDisponibles", objetsDisponibles); // Objets disponibles pour échange
        request.getRequestDispatcher("jsp/objets.jsp").forward(request, response);
    }


    /*** 5. Recherche d'objets ***/
    private void searchObjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Objet> objets = objectDAO.searchObjects(keyword);
        request.setAttribute("objects", objets);
        request.getRequestDispatcher("objets.jsp").forward(request, response);
    }

    private void requestExchange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de la session utilisateur
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        // Récupérer les paramètres envoyés par le formulaire
        String objectIdParam = request.getParameter("objectId");
        String selectedObjectIdParam = request.getParameter("selectedObjectId");

        // Vérification si les paramètres sont présents et non null
        if (objectIdParam == null || selectedObjectIdParam == null || objectIdParam.isEmpty() || selectedObjectIdParam.isEmpty()) {
            // Afficher un message d'erreur si l'un des paramètres est manquant
            System.out.println("objectIdParam: " + objectIdParam + ", selectedObjectIdParam: " + selectedObjectIdParam + " - Erreur : L'un des paramètres 'objectId' ou 'selectedObjectId' est manquant.");
            response.sendRedirect("jsp/error.jsp");
            return;
        }

        int objetDemandeId = Integer.parseInt(objectIdParam); // ID de l'objet à demander
        int objetProposeId = Integer.parseInt(selectedObjectIdParam); // ID de l'objet à proposer

        // Débogage : afficher les IDs des objets
        System.out.println("ID de l'objet demandé : " + objetDemandeId);
        System.out.println("ID de l'objet proposé : " + objetProposeId);

        // Vérification si les objets existent dans la base de données
        Objet objetDemande = objectDAO.getObjectById(objetDemandeId);
        Objet objetPropose = objectDAO.getObjectById(objetProposeId);

        if (objetDemande == null || objetPropose == null) {
            response.sendRedirect("jsp/error.jsp");
            return;
        }

        // Créer la demande d'échange
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(userId);

        Exchange exchange = new Exchange(objetPropose, objetDemande, utilisateur);
        exchangeDAO.requestExchange(exchange);

        response.sendRedirect("jsp/objets.jsp");
    }


    /*** 7. Accepter/Refuser un échange ***/
    private void manageExchange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int exchangeId = Integer.parseInt(request.getParameter("exchangeId"));
        String action = request.getParameter("decision"); // "accept" ou "reject"

        exchangeDAO.updateExchangeStatus(exchangeId, action);
        response.sendRedirect("jsp/dashboard.jsp");
    }

    /*** 8. Historique des échanges ***/
    private void showExchangeHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

    }

    private void showExchangePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        List<Objet> objetsUtilisateur = objectDAO.getObjectsByOwnerId(utilisateur.getId()); // Objets de l'utilisateur
        List<Objet> objetsDisponibles = objectDAO.getAllObjetsExceptOwner(utilisateur.getId()); // Exclure ses propres objets

        request.setAttribute("mesObjets", objetsUtilisateur);
        request.setAttribute("objetsDisponibles", objetsDisponibles);
        request.getRequestDispatcher("jsp/objets.jsp").forward(request, response);
    }

    private void showReceivedRequests(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {
        System.out.println("Here");

        // Initialisation de la session
        HttpSession session = request.getSession(true);

        // Récupérer l'ID utilisateur de la session
        Integer userId = (Integer) session.getAttribute("userId");

        // Vérification si l'utilisateur est connecté
        if (userId == null) {
            System.out.println("❌ [ERROR] Utilisateur non connecté - Redirection vers login.jsp");
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("🔍 [DEBUG] Utilisateur ID récupéré : " + userId);

        // Création d'une instance de ExchangeDAO pour récupérer les échanges reçus
        ExchangeDAO exchangeDAO = new ExchangeDAO();

        // Récupération des demandes reçues depuis la base de données
        List<Exchange> receivedRequests = exchangeDAO.getReceivedRequestsByUserId(userId);
        System.out.println("receivedRequests " + receivedRequests);

        // Si aucune demande reçue n'est trouvée
        if (receivedRequests == null || receivedRequests.isEmpty()) {
            System.out.println("❌ [ERROR] Aucune demande reçue trouvée pour l'utilisateur ID: " + userId);
            receivedRequests = new ArrayList<>();
        } else {
            System.out.println("✅ [SUCCESS] Nombre de demandes reçues trouvées : " + receivedRequests.size());
        }

        // Stockage des données dans la session
        session.setAttribute("receivedRequests", receivedRequests);

        // Redirection vers la page JSP pour afficher les demandes reçues
        request.getRequestDispatcher("/jsp/dashboardDemande.jsp").forward(request, response);
    }

}

