package tests;

import base.BaseTest;
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

    @Test(description = "Customer logout returns to login page")
    public void customerLogoutReturnsToLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        CustomerAccountPage account = loginPage.loginAsCustomer("Harry Potter");

        Assert.assertTrue(account.isLoggedIn(), "User should be logged in");

        // Click logout
        account.logout();

        // Wait for URL to change back to login page
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("#/login"));

        // Now verify login buttons are back
        LoginPage afterLogout = new LoginPage(driver);
        Assert.assertTrue(afterLogout.isCustomerLoginButtonVisible(),
                "Customer Login button should reappear after logout");
    }
}
