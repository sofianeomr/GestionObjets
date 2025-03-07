package org.gestionobjets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumFirefox {
    private static WebDriver driver;

    public SeleniumFirefox() {
        // Spécifier le chemin vers geckodriver
        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");

        // Configuration de Firefox
        FirefoxOptions options = new FirefoxOptions();

        // Initialiser le driver Firefox
        driver = new FirefoxDriver(options);
    }

    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void testRegisterUser() {
        driver.get("http://localhost:8081/GestionObjets_war/jsp/registration.jsp");

        // Use WebDriverWait to wait for elements to be present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Fill in the registration form
        WebElement nomField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("nom")));
        nomField.sendKeys("Test User");

        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        emailField.sendKeys("testuser@example.com");

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("motDePasse")));
        passwordField.sendKeys("password123");

        // Wait for the submit button to be clickable and then click it
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();

        // Verify the redirection after registration
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("login.jsp")) {
            System.out.println("Test d'inscription réussi.");
        } else {
            System.out.println("Test d'inscription échoué.");
        }
    }

    public void testLoginUser() {
        driver.get("http://localhost:8081/GestionObjets_war/jsp/login.jsp");

        // Remplir le formulaire de connexion
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("testuser@example.com");

        WebElement passwordField = driver.findElement(By.name("motDePasse"));
        passwordField.sendKeys("password123");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Vérifier la redirection après la connexion
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("objets.jsp")) {
            System.out.println("Test de connexion réussi.");
        } else {
            System.out.println("Test de connexion échoué.");
        }
    }

    public void testCreateObject() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/createObject.jsp");

        // Remplir le formulaire de création d'objet
        WebElement nomField = driver.findElement(By.name("nom"));
        nomField.sendKeys("Objet Test");

        WebElement categorieField = driver.findElement(By.name("categorie_id"));
        categorieField.sendKeys("1");

        WebElement descriptionField = driver.findElement(By.name("description"));
        descriptionField.sendKeys("Description de l'objet test");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Vérifier que l'objet est ajouté
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("objets.jsp")) {
            System.out.println("Test de création d'objet réussi.");
        } else {
            System.out.println("Test de création d'objet échoué.");
        }
    }

    public void testListObjects() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/objets.jsp");

        // Vérifier que les objets sont listés
        boolean objectsListed = driver.findElements(By.className("objet")).size() > 0;
        if (objectsListed) {
            System.out.println("Test de liste des objets réussi.");
        } else {
            System.out.println("Test de liste des objets échoué.");
        }
    }

    public void testSearchObjects() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/search.jsp");

        // Remplir le formulaire de recherche
        WebElement keywordField = driver.findElement(By.name("keyword"));
        keywordField.sendKeys("Test");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Vérifier que les résultats de recherche sont affichés
        boolean resultsFound = driver.findElements(By.className("objet")).size() > 0;
        if (resultsFound) {
            System.out.println("Test de recherche d'objets réussi.");
        } else {
            System.out.println("Test de recherche d'objets échoué.");
        }
    }

    public void testRequestExchange() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/requestExchange.jsp");

        // Remplir le formulaire de demande d'échange
        WebElement objectIdField = driver.findElement(By.name("objectId"));
        objectIdField.sendKeys("1");

        WebElement selectedObjectIdField = driver.findElement(By.name("selectedObjectId"));
        selectedObjectIdField.sendKeys("2");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Vérifier que la demande d'échange est enregistrée
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("objets.jsp")) {
            System.out.println("Test de demande d'échange réussi.");
        } else {
            System.out.println("Test de demande d'échange échoué.");
        }
    }

    public void testManageExchange() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/manageExchange.jsp");

        // Accepter une demande d'échange
        WebElement exchangeIdField = driver.findElement(By.name("exchangeId"));
        exchangeIdField.sendKeys("1");

        WebElement decisionField = driver.findElement(By.name("decision"));
        decisionField.sendKeys("accept");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Vérifier que la demande d'échange est mise à jour
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("dashboard.jsp")) {
            System.out.println("Test de gestion d'échange réussi.");
        } else {
            System.out.println("Test de gestion d'échange échoué.");
        }
    }

    public void testShowExchangeHistory() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/history.jsp");

        // Vérifier que l'historique des échanges est affiché
        boolean historyDisplayed = driver.findElements(By.className("exchange")).size() > 0;
        if (historyDisplayed) {
            System.out.println("Test d'historique des échanges réussi.");
        } else {
            System.out.println("Test d'historique des échanges échoué.");
        }
    }

    public static void main(String[] args) {
        SeleniumFirefox testSuite = new SeleniumFirefox();

        try {
            System.out.println("Exécution du test : testRegisterUser");
            testSuite.testRegisterUser();

            System.out.println("Exécution du test : testLoginUser");
            testSuite.testLoginUser();

            System.out.println("Exécution du test : testCreateObject");
            testSuite.testCreateObject();

            System.out.println("Exécution du test : testListObjects");
            testSuite.testListObjects();

            System.out.println("Exécution du test : testSearchObjects");
            testSuite.testSearchObjects();

            System.out.println("Exécution du test : testRequestExchange");
            testSuite.testRequestExchange();

            System.out.println("Exécution du test : testManageExchange");
            testSuite.testManageExchange();

            System.out.println("Exécution du test : testShowExchangeHistory");
            testSuite.testShowExchangeHistory();
        } finally {
            testSuite.close();
        }
    }
}
