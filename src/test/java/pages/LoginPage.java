package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object for the XYZ Bank login page.
 * Two login types: Customer Login and Bank Manager Login.
 */
public class LoginPage extends BasePage {

    private final By customerLoginBtn  = By.xpath("//button[contains(text(),'Customer Login')]");
    private final By managerLoginBtn   = By.xpath("//button[contains(text(),'Bank Manager Login')]");
    private final By customerDropdown  = By.id("userSelect");
    private final By loginSubmitBtn    = By.xpath("//button[@type='submit' and contains(text(),'Login')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Click Bank Manager Login button. */
    public ManagerPage loginAsManager() {
        waitForClickable(managerLoginBtn).click();
        return new ManagerPage(driver);
    }

    /** Select customer from dropdown and click Login. */
    public CustomerAccountPage loginAsCustomer(String customerName) {
        waitForClickable(customerLoginBtn).click();
        WebElement dropdown = waitForVisible(customerDropdown);
        new Select(dropdown).selectByVisibleText(customerName);
        waitForClickable(loginSubmitBtn).click();
        return new CustomerAccountPage(driver);
    }

    /** Check if Customer Login button is present. */
    public boolean isCustomerLoginButtonVisible() {
        return !driver.findElements(customerLoginBtn).isEmpty();
    }

    /** Check if Manager Login button is present. */
    public boolean isManagerLoginButtonVisible() {
        return !driver.findElements(managerLoginBtn).isEmpty();
    }

    /** Get Customer Login button text. */
    public String getCustomerLoginButtonText() {
        return waitForVisible(customerLoginBtn).getText();
    }
}
