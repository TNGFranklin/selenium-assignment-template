package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CustomerAccountPage;
import pages.LoginPage;
import pages.ManagerPage;

import java.time.Duration;

/**
 * Tests for login functionality on XYZ Bank.
 * Covers: customer login (dropdown), manager login, logout, page title.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Verify the XYZ Bank login page title")
    public void verifyLoginPageTitle() {
        LoginPage loginPage = new LoginPage(driver);
        String title = loginPage.getPageTitle();
        Assert.assertFalse(title.isEmpty(),
                "Page title should not be empty, got: " + title);
        System.out.println("Login page title: " + title);
    }

    @Test(description = "Verify Customer Login and Bank Manager Login buttons are present")
    public void verifyLoginButtonsPresent() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isCustomerLoginButtonVisible(),
                "Customer Login button should be visible");
        Assert.assertTrue(loginPage.isManagerLoginButtonVisible(),
                "Bank Manager Login button should be visible");
    }

    @Test(description = "Customer login with Harry Potter shows account dashboard")
    public void customerLoginWithHarryPotter() {
        LoginPage loginPage = new LoginPage(driver);
        CustomerAccountPage account = loginPage.loginAsCustomer("Harry Potter");
        Assert.assertTrue(account.isAccountDashboardDisplayed(),
                "Account dashboard should be displayed after customer login");
    }

    @Test(description = "Customer login with Hermoine Granger shows account dashboard")
    public void customerLoginWithHermoineGranger() {
        LoginPage loginPage = new LoginPage(driver);
        CustomerAccountPage account = loginPage.loginAsCustomer("Hermoine Granger");
        Assert.assertTrue(account.isAccountDashboardDisplayed(),
                "Account dashboard should be displayed after login");
    }

    @Test(description = "Bank Manager login shows manager dashboard")
    public void managerLoginShowsDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        ManagerPage managerPage = loginPage.loginAsManager();
        Assert.assertTrue(managerPage.isManagerPageDisplayed(),
                "Manager dashboard should be displayed after manager login");
    }

    @Test(description = "Customer logout removes account dashboard from view")
    public void customerLogoutReturnsToLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        CustomerAccountPage account = loginPage.loginAsCustomer("Harry Potter");

        // Verify logged in — deposit button visible
        Assert.assertTrue(account.isAccountDashboardDisplayed(),
                "Deposit button should be visible before logout");

        // Click logout
        account.logout();

        // Wait for deposit button to disappear — confirms logout worked
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//button[contains(text(),'Deposit')]")));

        // Verify deposit button is gone
        Assert.assertTrue(
                driver.findElements(By.xpath("//button[contains(text(),'Deposit')]")).isEmpty(),
                "Deposit button should be gone after logout");

        System.out.println("URL after logout: " + driver.getCurrentUrl());
    }
}
