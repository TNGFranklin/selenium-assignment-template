package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ManagerPage;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for navigation, static content, and multiple page verification.
 * Covers: static page test, multiple page test, complex XPath, page title.
 */
public class NavigationTest extends BaseTest {

    @Test(description = "Verify XYZ Bank home page has correct static content")
    public void verifyHomePageStaticContent() {
        // static_page_test task
        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isCustomerLoginButtonVisible(),
                "Customer Login button should be on home page");
        Assert.assertTrue(loginPage.isManagerLoginButtonVisible(),
                "Bank Manager Login button should be on home page");

        String btnText = loginPage.getCustomerLoginButtonText();
        Assert.assertFalse(btnText.isEmpty(),
                "Button text should not be empty");
    }

    @Test(description = "Verify multiple XYZ Bank pages load with correct URL fragment")
    public void verifyMultiplePagesLoad() {
        // multiple_page_test task — iterate over URL fragments
        List<String> urlFragments = Arrays.asList(
                "#/login",
                "#/manager",
                "#/customer"
        );

        String baseUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/";

        for (String fragment : urlFragments) {
            driver.get(baseUrl + fragment);
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("BankingProject"),
                    "Should be on XYZ Bank for fragment: " + fragment);
            System.out.println("Visited: " + currentUrl);
        }
    }

    @Test(description = "Use complex XPath to find all buttons on login page")
    public void findAllButtonsUsingComplexXPath() {
        // complex_xpath: buttons with ng-click attribute (AngularJS)
        List<WebElement> ngButtons = driver.findElements(
                By.xpath("//button[@ng-click]"));
        Assert.assertFalse(ngButtons.isEmpty(),
                "Should find AngularJS buttons using complex XPath");
        System.out.println("ng-click buttons found: " + ngButtons.size());
    }

    @Test(description = "Use complex XPath to find heading elements on login page")
    public void findHeadingsUsingComplexXPath() {
        // complex_xpath: h2 inside the main body
        List<WebElement> headings = driver.findElements(
                By.xpath("//div[@class='center']//h2"));
        System.out.println("Headings found: " + headings.size());
        Assert.assertNotNull(headings, "XPath should execute without error");
    }

    @Test(description = "Verify page title on login page using getTitle()")
    public void verifyPageTitleOnLoginPage() {
        // page_title task
        LoginPage loginPage = new LoginPage(driver);
        String title = loginPage.getPageTitle();
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
        System.out.println("Login page title: " + title);
    }

    @Test(description = "Manager page has Add Customer, Open Account, Customers buttons")
    public void verifyManagerPageStaticContent() {
        ManagerPage manager = new LoginPage(driver).loginAsManager();

        // Verify three key buttons present using complex XPath
        WebElement addBtn = driver.findElement(
                By.xpath("//button[contains(@ng-click,'addCust')]"));
        WebElement openBtn = driver.findElement(
                By.xpath("//button[contains(@ng-click,'openAccount')]"));
        WebElement custBtn = driver.findElement(
                By.xpath("//button[contains(@ng-click,'showCust')]"));

        Assert.assertTrue(addBtn.isDisplayed(), "Add Customer button should be visible");
        Assert.assertTrue(openBtn.isDisplayed(), "Open Account button should be visible");
        Assert.assertTrue(custBtn.isDisplayed(), "Customers button should be visible");
    }
}
