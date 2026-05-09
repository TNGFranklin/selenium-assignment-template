package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the Customer Account page after login.
 * Shows Welcome message, account info, and action buttons.
 */
public class CustomerAccountPage extends BasePage {

    private final By welcomeMessage    = By.cssSelector("span.ng-binding");
    private final By accountDropdown   = By.id("accountSelect");
    private final By transactionsBtn   = By.xpath("//button[contains(text(),'Transactions')]");
    private final By depositBtn        = By.xpath("//button[contains(text(),'Deposit')]");
    private final By withdrawlBtn      = By.xpath("//button[contains(text(),'Withdrawl')]");
    private final By logoutBtn         = By.xpath("//button[contains(text(),'Logout')]");
    private final By amountInput       = By.xpath("//input[@placeholder='amount']");
    private final By depositSubmitBtn  = By.xpath("//button[@type='submit' and contains(text(),'Deposit')]");
    private final By withdrawSubmitBtn = By.xpath("//button[@type='submit' and contains(text(),'Withdraw')]");
    private final By successMessage    = By.cssSelector("span.error");
    private final By accountBody       = By.cssSelector("div.center");

    public CustomerAccountPage(WebDriver driver) {
        super(driver);
    }

    /** Get the welcome message text. */
    public String getWelcomeMessage() {
        // The heading is like "Welcome Harry Potter !!"
        WebElement heading = waitForVisible(By.xpath("//div[@class='center']/h2"));
        return heading.getText();
    }

    /** Check if account dashboard is displayed. */
    public boolean isAccountDashboardDisplayed() {
        return !driver.findElements(depositBtn).isEmpty();
    }

    /** Click Deposit button and deposit an amount. */
    public void deposit(String amount) {
        waitForClickable(depositBtn).click();
        WebElement input = waitForVisible(amountInput);
        input.clear();
        input.sendKeys(amount);
        waitForClickable(depositSubmitBtn).click();
    }

    /** Click Withdrawl button and withdraw an amount. */
    public void withdraw(String amount) {
        waitForClickable(withdrawlBtn).click();
        WebElement input = waitForVisible(amountInput);
        input.clear();
        input.sendKeys(amount);
        waitForClickable(withdrawSubmitBtn).click();
    }

    /** Click Transactions button to view transaction history. */
    public void viewTransactions() {
        waitForClickable(transactionsBtn).click();
    }

    /** Get the success/status message after deposit or withdrawal. */
    public String getStatusMessage() {
        return waitForVisible(successMessage).getText();
    }

    /** Logout from customer account. */
    public LoginPage logout() {
        waitForClickable(logoutBtn).click();
        return new LoginPage(driver);
    }

    /** Check if logout button is visible (user is logged in). */
    public boolean isLoggedIn() {
        return !driver.findElements(logoutBtn).isEmpty();
    }

    /** Get the account body element for JS scroll testing. */
    public WebElement getAccountBody() {
        return waitForVisible(accountBody);
    }
}
