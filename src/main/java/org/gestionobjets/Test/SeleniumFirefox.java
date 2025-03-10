package org.gestionobjets.Test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumFirefox {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Spécifier le chemin vers geckodriver
        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");

        // Configuration de Firefox
        FirefoxOptions options = new FirefoxOptions();

        // Initialiser le driver Firefox
        driver = new FirefoxDriver(options);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testRegisterUser() {
        driver.get("http://localhost:8081/GestionObjets_war/jsp/registration.jsp");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement nomField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("nom")));
        nomField.sendKeys("Test User");

        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        emailField.sendKeys("testuserSelinium@example.com");

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("motDePasse")));
        passwordField.sendKeys("password123");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Registration failed.", currentUrl.contains("login.jsp"));
    }

    @Test
    public void testLoginUser() {
        driver.get("http://localhost:8081/GestionObjets_war/jsp/login.jsp");

        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("testuser@example.com");

        WebElement passwordField = driver.findElement(By.name("motDePasse"));
        passwordField.sendKeys("password123");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Login failed.", currentUrl.contains("objets.jsp"));
    }

    @Test
    public void testCreateObject() {
        testLoginUser(); // Ensure the user is logged in

        driver.get("http://localhost:8081/GestionObjets_war/jsp/objets.jsp");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createObjectButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.create-object-btn")));
        createObjectButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("createObjectModal")));

        WebElement nomField = driver.findElement(By.id("objectName"));
        nomField.sendKeys("Objet Test");

        WebElement descriptionField = driver.findElement(By.id("objectDescription"));
        descriptionField.sendKeys("Description de l'objet test");

        WebElement categorieField = driver.findElement(By.id("objectCategory"));
        categorieField.sendKeys("3"); // Assuming "3" is the value for a valid category

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        wait.until(ExpectedConditions.urlContains("objets.jsp"));
        WebElement newObject = driver.findElement(By.xpath("//h3[text()='Objet Test']"));
        Assert.assertTrue("Object not created successfully.", newObject.isDisplayed());
    }

    @Test
    public void testListObjects() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/objets.jsp");

        boolean objectsListed = driver.findElements(By.className("objet")).size() > 0;
        Assert.assertTrue("No objects listed.", objectsListed);
    }

    @Test
    public void testSearchObjects() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/search.jsp");

        WebElement keywordField = driver.findElement(By.name("keyword"));
        keywordField.sendKeys("Test");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        boolean resultsFound = driver.findElements(By.className("objet")).size() > 0;
        Assert.assertTrue("No search results found.", resultsFound);
    }

    @Test
    public void testRequestExchange() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/requestExchange.jsp");

        WebElement objectIdField = driver.findElement(By.name("objectId"));
        objectIdField.sendKeys("1");

        WebElement selectedObjectIdField = driver.findElement(By.name("selectedObjectId"));
        selectedObjectIdField.sendKeys("2");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Exchange request failed.", currentUrl.contains("objets.jsp"));
    }

    @Test
    public void testManageExchange() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/manageExchange.jsp");

        WebElement exchangeIdField = driver.findElement(By.name("exchangeId"));
        exchangeIdField.sendKeys("1");

        WebElement decisionField = driver.findElement(By.name("decision"));
        decisionField.sendKeys("accept");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Exchange management failed.", currentUrl.contains("dashboard.jsp"));
    }

    @Test
    public void testShowExchangeHistory() {
        testLoginUser(); // Se connecter d'abord

        driver.get("http://localhost:8081/GestionObjets_war/jsp/history.jsp");

        boolean historyDisplayed = driver.findElements(By.className("exchange")).size() > 0;
        Assert.assertTrue("Exchange history not displayed.", historyDisplayed);
    }
}
