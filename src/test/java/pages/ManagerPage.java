package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object for the Bank Manager page.
 * Contains Add Customer, Open Account, and Customers sections.
 */
public class ManagerPage extends BasePage {

    private final By addCustomerBtn    = By.xpath("//button[contains(text(),'Add Customer')]");
    private final By openAccountBtn    = By.xpath("//button[contains(text(),'Open Account')]");
    private final By customersBtn      = By.xpath("//button[contains(text(),'Customers')]");

    // Add Customer form
    private final By firstNameInput    = By.xpath("//input[@placeholder='First Name']");
    private final By lastNameInput     = By.xpath("//input[@placeholder='Last Name']");
    private final By postCodeInput     = By.xpath("//input[@placeholder='Post Code']");
    private final By addCustomerSubmit = By.xpath("//button[@type='submit' and contains(text(),'Add Customer')]");

    // Open Account form
    private final By customerSelect    = By.id("userSelect");
    private final By currencySelect    = By.id("currency");
    private final By processBtn        = By.xpath("//button[@type='submit' and contains(text(),'Process')]");

    // Customers table
    private final By customerRows      = By.xpath("//table/tbody/tr");
    private final By searchInput       = By.xpath("//input[@placeholder='Search Customer']");
    private final By sortFirstName     = By.xpath("//a[contains(text(),'First Name')]");

    public ManagerPage(WebDriver driver) {
        super(driver);
    }

    /** Click Add Customer button. */
    public void clickAddCustomer() {
        waitForClickable(addCustomerBtn).click();
    }

    /** Fill and submit the Add Customer form. */
    public void addCustomer(String firstName, String lastName, String postCode) {
        clickAddCustomer();

        WebElement fn = waitForVisible(firstNameInput);
        fn.clear();
        fn.sendKeys(firstName);

        WebElement ln = waitForVisible(lastNameInput);
        ln.clear();
        ln.sendKeys(lastName);

        WebElement pc = waitForVisible(postCodeInput);
        pc.clear();
        pc.sendKeys(postCode);

        waitForClickable(addCustomerSubmit).click();
    }

    /** Click Open Account button. */
    public void clickOpenAccount() {
        waitForClickable(openAccountBtn).click();
    }

    /** Open an account for a customer with given currency. */
    public void openAccount(String customerName, String currency) {
        clickOpenAccount();

        Select custSel = new Select(waitForVisible(customerSelect));
        custSel.selectByVisibleText(customerName);

        Select currSel = new Select(waitForVisible(currencySelect));
        currSel.selectByVisibleText(currency);

        waitForClickable(processBtn).click();
    }

    /** Click Customers button to view customer list. */
    public void clickCustomers() {
        waitForClickable(customersBtn).click();
    }

    /** Get count of customer rows in the table. */
    public int getCustomerCount() {
        clickCustomers();
        return driver.findElements(customerRows).size();
    }

    /** Search for a customer by name. */
    public void searchCustomer(String name) {
        clickCustomers();
        WebElement search = waitForVisible(searchInput);
        search.clear();
        search.sendKeys(name);
    }

    /** Get all customer rows. */
    public List<WebElement> getCustomerRows() {
        return driver.findElements(customerRows);
    }

    /** Sort customer list by first name. */
    public void sortByFirstName() {
        waitForClickable(sortFirstName).click();
    }

    /** Check if Add Customer button is visible. */
    public boolean isManagerPageDisplayed() {
        return !driver.findElements(addCustomerBtn).isEmpty();
    }

    /** Click Home button. */
    public void goHome() {
        driver.findElement(By.xpath("//button[contains(text(),'Home')]")).click();
    }
}
