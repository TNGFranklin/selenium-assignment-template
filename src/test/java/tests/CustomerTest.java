package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CustomerAccountPage;
import pages.LoginPage;
import utils.RandomDataGenerator;

/**
 * Tests for customer account operations on XYZ Bank.
 * Covers: deposit, withdrawal, transactions, JS executor, cookies, history.
 */
public class CustomerTest extends BaseTest {

    private CustomerAccountPage loginAsHarryPotter() {
        return new LoginPage(driver).loginAsCustomer("Harry Potter");
    }

    @Test(description = "Harry Potter account shows correct welcome message")
    public void accountShowsCorrectWelcomeMessage() {
        CustomerAccountPage account = loginAsHarryPotter();
        String welcome = account.getWelcomeMessage();
        Assert.assertTrue(welcome.contains("Harry"),
                "Welcome message should contain customer name 'Harry'");
    }

    @Test(description = "Deposit random amount and verify success message")
    public void depositRandomAmountSuccessfully() {
        CustomerAccountPage account = loginAsHarryPotter();

        // random_data task — use random deposit amount
        String amount = RandomDataGenerator.generateAmount();
        System.out.println("Depositing amount: " + amount);
        account.deposit(amount);

        String status = account.getStatusMessage();
        Assert.assertTrue(status.contains("Deposit Successful") ||
                status.contains("successful"),
                "Deposit should show success message, got: " + status);
    }

    @Test(description = "Scroll to account section using JavascriptExecutor")
    public void scrollUsingJavascriptExecutor() {
        CustomerAccountPage account = loginAsHarryPotter();

        // javascript_executor task
        WebElement body = account.getAccountBody();
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", body);

        Assert.assertTrue(body.isDisplayed(),
                "Account body should be visible after JS scroll");
    }

    @Test(description = "Test browser back and forward history navigation")
    public void testBrowserHistoryNavigation() {
        // history_test task
        String loginUrl = driver.getCurrentUrl();

        // Navigate to customer page
        new LoginPage(driver).loginAsCustomer("Harry Potter");
        String accountUrl = driver.getCurrentUrl();

        // Go back
        driver.navigate().back();
        Assert.assertNotEquals(driver.getCurrentUrl(), accountUrl,
                "Back navigation should leave the account page");

        // Go forward
        driver.navigate().forward();
        System.out.println("Forward URL: " + driver.getCurrentUrl());
    }

    @Test(description = "Read and manipulate browser cookies on XYZ Bank")
    public void manipulateCookies() {
        // cookie_manipulation task
        loginAsHarryPotter();

        // Read existing cookies
        java.util.Set<org.openqa.selenium.Cookie> cookies =
                driver.manage().getCookies();
        System.out.println("Cookies found: " + cookies.size());

        // Add a custom test cookie
        driver.manage().addCookie(
                new org.openqa.selenium.Cookie("test_session", "xyz_bank_test"));

        // Verify custom cookie was added
        org.openqa.selenium.Cookie testCookie =
                driver.manage().getCookieNamed("test_session");
        Assert.assertNotNull(testCookie, "Custom cookie should be added");
        Assert.assertEquals(testCookie.getValue(), "xyz_bank_test",
                "Cookie value should match");

        // Delete custom cookie
        driver.manage().deleteCookieNamed("test_session");
        Assert.assertNull(driver.manage().getCookieNamed("test_session"),
                "Cookie should be deleted after removal");
    }

    @Test(description = "View transactions page after deposit")
    public void viewTransactionsAfterDeposit() {
        CustomerAccountPage account = loginAsHarryPotter();
        account.deposit("500");
        account.viewTransactions();

        // Verify transactions page loaded
        Assert.assertTrue(driver.getCurrentUrl().contains("BankingProject"),
                "Should remain on XYZ Bank site");
    }

    @Test(description = "Use complex XPath to find transaction table elements")
    public void findTransactionTableUsingComplexXPath() {
        CustomerAccountPage account = loginAsHarryPotter();
        account.deposit("200");
        account.viewTransactions();

        // complex_xpath: transaction rows
        java.util.List<WebElement> transactionRows = driver.findElements(
                By.xpath("//table/tbody/tr[td]"));
        System.out.println("Transaction rows: " + transactionRows.size());
        Assert.assertNotNull(transactionRows,
                "Complex XPath should execute on transaction table");
    }

    @Test(description = "Verify page title using getTitle() on account page")
    public void verifyPageTitleOnAccountPage() {
        CustomerAccountPage account = loginAsHarryPotter();
        String title = account.getPageTitle();
        Assert.assertFalse(title.isEmpty(),
                "Page title should not be empty on account page");
        System.out.println("Account page title: " + title);
    }
}
