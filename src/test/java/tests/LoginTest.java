package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CustomerAccountPage;
import pages.LoginPage;
import pages.ManagerPage;

/**
 * Tests for login functionality on XYZ Bank.
 * Covers: customer login (dropdown), manager login, logout, page title.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Verify the XYZ Bank login page title")
    public void verifyLoginPageTitle() {
        LoginPage loginPage = new LoginPage(driver);
        String title = loginPage.getPageTitle();
        Assert.assertTrue(title.contains("XYZ Bank") || title.contains("GlobalSQA"),
                "Page title should contain 'XYZ Bank' or 'GlobalSQA', got: " + title);
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
        Assert.assertTrue(account.getWelcomeMessage().contains("Harry Potter"),
                "Welcome message should contain customer name");
    }

    @Test(description = "Customer login with Hermoine Granger shows account dashboard")
    public void customerLoginWithHermoineGranger() {
        LoginPage loginPage = new LoginPage(driver);
        CustomerAccountPage account = loginPage.loginAsCustomer("Hermoine Granger");

        Assert.assertTrue(account.isAccountDashboardDisplayed(),
                "Account dashboard should be displayed after login");
    }

    @Test(description = "Bank Manager login shows manager dashboard",
          groups = {"manager"})
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

        LoginPage afterLogout = account.logout();

        Assert.assertTrue(afterLogout.isCustomerLoginButtonVisible(),
                "Should return to login page after logout");
    }
}
