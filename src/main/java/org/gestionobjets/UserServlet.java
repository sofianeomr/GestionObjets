package org.gestionobjets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.gestionobjets.dao.UserDAO;
import org.gestionobjets.dao.ObjectDAO;
import org.gestionobjets.dao.ExchangeDAO;
import org.gestionobjets.models.Exchange;
import org.gestionobjets.models.Utilisateur;
import org.gestionobjets.models.Objet;

import java.io.IOException;
import java.util.List;

@WebServlet("/userServlet")
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
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    /*** 1. Création de compte ***/
    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("password");
        //String motDePasse = request.getParameter("motDePasse");

        Utilisateur utilisateur = new Utilisateur(nom, email, motDePasse);
        boolean success = userDAO.registerUser(utilisateur);

        if (success) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("errorMessage", "Erreur lors de l'inscription.");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        }
    }

    /*** 2. Connexion utilisateur ***/
    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Utilisateur utilisateur = userDAO.connexionUser(email, motDePasse);

        if (utilisateur != null) {
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", utilisateur);
            List<Objet> objets = objectDAO.getAllObjets();
            request.setAttribute("objects", objets);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);

            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("errorMessage", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /*** 3. Création d'un objet ***/
    private void createObject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur Utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (Utilisateur == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String nom = request.getParameter("nom");
        String categorie = request.getParameter("categorie");
        String description = request.getParameter("description");
        //String proprietaire= request.getParameter("proprietaire");

        Objet objet = new Objet(nom, categorie, description, Utilisateur.getId());
        objectDAO.addObject(objet);

        List<Objet> objets = objectDAO.getAllObjets();
        request.setAttribute("objects", objets);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);


        response.sendRedirect("dashboard.jsp");
    }

    /*** 4. Liste des objets ***/
    private void listObjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Objet> objets = objectDAO.getAllObjets();
        request.setAttribute("objects", objets);
        request.getRequestDispatcher("objects.jsp").forward(request, response);
    }

    /*** 5. Recherche d'objets ***/
    private void searchObjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Objet> objets = objectDAO.searchObjects(keyword);
        request.setAttribute("objects", objets);
        request.getRequestDispatcher("objets.jsp").forward(request, response);
    }

    /*** 6. Demande d'échange ***/
    private void requestExchange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int objectId = Integer.parseInt(request.getParameter("objectId"));
        int ownerId = Integer.parseInt(request.getParameter("ownerId"));

        Objet objetPropose = objectDAO.getObjectById(objectId);
        //Objet objetDemande = objectDAO.getObjectByOwnerId(ownerId);
        Objet objetDemande = objectDAO.getObjectById(objectId);


        if (objetPropose == null || objetDemande == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        Exchange exchange = new Exchange(utilisateur, objetPropose, objetDemande);
        exchangeDAO.requestExchange(exchange);

        response.sendRedirect("objets.jsp");
    }

    /*** 7. Accepter/Refuser un échange ***/
    private void manageExchange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int exchangeId = Integer.parseInt(request.getParameter("exchangeId"));
        String action = request.getParameter("decision"); // "accept" ou "reject"

        exchangeDAO.updateExchangeStatus(exchangeId, action);
        response.sendRedirect("dashboard.jsp");
    }

    /*** 8. Historique des échanges ***/
    private void showExchangeHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Exchange> history = exchangeDAO.getUserExchangeHistory(utilisateur.getId());
        request.setAttribute("history", history);
        request.getRequestDispatcher("history.jsp").forward(request, response);
    }
}

