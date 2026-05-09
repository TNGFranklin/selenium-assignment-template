package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.RandomDataGenerator;

/**
 * Tests for login functionality on demo.nopcommerce.com.
 * Covers: login form, failed login, logout, page title.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Verify the login page title is correct")
    public void verifyLoginPageTitle() {
        driver.get(ConfigReader.getBaseUrl() + "/login");
        LoginPage loginPage = new LoginPage(driver);

        String title = loginPage.getPageTitle();
        Assert.assertTrue(title.contains("Login"), "Page title should contain 'Login', got: " + title);
    }

    @Test(description = "Login with invalid credentials shows error message")
    public void loginWithInvalidCredentialsShowsError() {
        driver.get(ConfigReader.getBaseUrl() + "/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginWith("invalid@notexist.com", "wrongpassword123");

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed after invalid login");
    }

    @Test(description = "Login with empty credentials shows validation error")
    public void loginWithEmptyCredentialsShowsError() {
        driver.get(ConfigReader.getBaseUrl() + "/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isErrorDisplayed() ||
                driver.getCurrentUrl().contains("login"),
                "Should remain on login page or show error for empty credentials");
    }

    @Test(description = "Register a new user then log in successfully",
          groups = {"login"})
    public void registerThenLoginSuccessfully() {
        // Generate random credentials (random_data task)
        String email    = RandomDataGenerator.generateEmail();
        String password = RandomDataGenerator.generatePassword();
        String firstName = RandomDataGenerator.generateFirstName();
        String lastName  = RandomDataGenerator.generateLastName();

        // Register
        driver.get(ConfigReader.getBaseUrl() + "/register");
        new pages.RegisterPage(driver).registerUser(firstName, lastName, email, password);

        // Logout to get a clean state
        driver.get(ConfigReader.getBaseUrl() + "/logout");

        // Login with newly created credentials
        driver.get(ConfigReader.getBaseUrl() + "/login");
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.loginWith(email, password);

        Assert.assertTrue(homePage.isLoggedIn(),
                "User should be logged in after successful login");
    }

    @Test(description = "Logout from application and verify redirect to home page",
          dependsOnMethods = {"registerThenLoginSuccessfully"},
          groups = {"login"})
    public void logoutSuccessfully() {
        // Register and login first
        String email    = RandomDataGenerator.generateEmail();
        String password = RandomDataGenerator.generatePassword();

        driver.get(ConfigReader.getBaseUrl() + "/register");
        new pages.RegisterPage(driver).registerUser(
                RandomDataGenerator.generateFirstName(),
                RandomDataGenerator.generateLastName(),
                email, password);

        driver.get(ConfigReader.getBaseUrl() + "/logout");
        driver.get(ConfigReader.getBaseUrl() + "/login");
        new LoginPage(driver).loginWith(email, password);

        // Logout
        HomePage homePage = new HomePage(driver);
        homePage.logout();

        Assert.assertFalse(homePage.isLoggedIn(),
                "User should be logged out after clicking logout");
        Assert.assertTrue(driver.getCurrentUrl().contains(ConfigReader.getBaseUrl()),
                "Should redirect to home page after logout");
    }
}
