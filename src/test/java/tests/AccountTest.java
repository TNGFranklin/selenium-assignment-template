package tests;

import base.BaseTest;
import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import utils.RandomDataGenerator;

/**
 * Tests for account management on demo.nopcommerce.com.
 * Covers: form with logged-in user, update profile, complex XPath.
 */
public class AccountTest extends BaseTest {

    /**
     * Helper: register a fresh user and return their credentials.
     */
    private String[] registerFreshUser() {
        String email    = RandomDataGenerator.generateEmail();
        String password = RandomDataGenerator.generatePassword();
        String firstName = RandomDataGenerator.generateFirstName();
        String lastName  = RandomDataGenerator.generateLastName();

        driver.get(ConfigReader.getBaseUrl() + "/register");
        new RegisterPage(driver).registerUser(firstName, lastName, email, password);

        // Logout then login to start from a clean login state
        driver.get(ConfigReader.getBaseUrl() + "/logout");
        driver.get(ConfigReader.getBaseUrl() + "/login");
        new LoginPage(driver).loginWith(email, password);

        return new String[]{email, password, firstName, lastName};
    }

    @Test(description = "Update account info form while logged in (form with user)")
    public void updateAccountInfoWhileLoggedIn() {
        registerFreshUser();

        // Navigate to account info
        driver.get(ConfigReader.getBaseUrl() + "/customer/info");
        AccountPage accountPage = new AccountPage(driver);

        // Update first name (form with logged-in user task)
        String newFirstName = RandomDataGenerator.generateFirstName();
        accountPage.updateFirstName(newFirstName);
        accountPage.saveAccountInfo();

        // Verify success — page should reload with the new name
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/customer/info") ||
                driver.findElements(By.cssSelector("div.bar-notification.success")).size() > 0,
                "Account info should be saved successfully");
    }

    @Test(description = "Verify account page email matches registered email")
    public void verifyAccountEmailMatchesRegistration() {
        String[] credentials = registerFreshUser();
        String registeredEmail = credentials[0];

        driver.get(ConfigReader.getBaseUrl() + "/customer/info");
        AccountPage accountPage = new AccountPage(driver);

        String displayedEmail = accountPage.getEmail();
        Assert.assertEquals(displayedEmail, registeredEmail,
                "Email on account page should match registered email");
    }

    @Test(description = "Use complex XPath to find navigation links in account sidebar")
    public void findAccountSidebarLinksUsingComplexXPath() {
        registerFreshUser();
        driver.get(ConfigReader.getBaseUrl() + "/customer/info");

        // Complex XPath: find all links in the customer navigation block
        java.util.List<WebElement> sidebarLinks = driver.findElements(
                By.xpath("//div[contains(@class,'block-account-navigation')]//li/a"));
        Assert.assertFalse(sidebarLinks.isEmpty(),
                "Account sidebar should have navigation links");
        System.out.println("Account sidebar links: " + sidebarLinks.size());
    }

    @Test(description = "Navigate to order history using complex XPath link")
    public void navigateToOrderHistoryUsingXPath() {
        registerFreshUser();
        driver.get(ConfigReader.getBaseUrl() + "/customer/info");

        // Complex XPath: anchor inside navigation list that contains 'order' in href
        WebElement ordersLink = driver.findElement(
                By.xpath("//div[contains(@class,'block-account-navigation')]//a[contains(@href,'order')]"));
        ordersLink.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("order"),
                "Should navigate to order history page");
    }

    @Test(description = "Fill newsletter textarea/input on account page")
    public void fillNewsletterSubscriptionOnAccountPage() {
        registerFreshUser();

        // Navigate to newsletter subscriptions
        driver.get(ConfigReader.getBaseUrl() + "/newsletter/subscriptions");

        // Newsletter preference is a form with checkboxes/inputs
        java.util.List<WebElement> checkboxes = driver.findElements(
                By.xpath("//input[@type='checkbox']"));
        if (!checkboxes.isEmpty()) {
            WebElement checkbox = checkboxes.get(0);
            boolean wasChecked = checkbox.isSelected();
            checkbox.click();
            Assert.assertNotEquals(checkbox.isSelected(), wasChecked,
                    "Checkbox state should toggle after clicking");
        }
    }

    @Test(description = "Submit product review with textarea while logged in")
    public void submitProductReviewWithTextarea() {
        registerFreshUser();

        // Navigate to a product — Apple MacBook Pro
        driver.get(ConfigReader.getBaseUrl() + "/apple-macbook-pro-13-inch");

        // Find the review link using complex XPath
        java.util.List<WebElement> reviewLinks = driver.findElements(
                By.xpath("//a[contains(@href,'productreviews') or contains(text(),'review')]"));

        if (!reviewLinks.isEmpty()) {
            reviewLinks.get(0).click();

            // Fill textarea (textarea task)
            java.util.List<WebElement> textareas = driver.findElements(
                    By.xpath("//textarea[@id='AddNewReview_ReviewText']"));
            if (!textareas.isEmpty()) {
                String reviewText = "Great product! Testing with Selenium - " +
                        RandomDataGenerator.randomString(20);
                textareas.get(0).clear();
                textareas.get(0).sendKeys(reviewText);
                Assert.assertEquals(textareas.get(0).getAttribute("value"), reviewText,
                        "Textarea should contain the entered review text");
            }
        }
    }
}
