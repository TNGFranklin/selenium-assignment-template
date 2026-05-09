package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the nopCommerce My Account page.
 */
public class AccountPage extends BasePage {

    private final By firstNameField    = By.id("FirstName");
    private final By lastNameField     = By.id("LastName");
    private final By emailField        = By.id("Email");
    private final By saveButton        = By.cssSelector("button.save-customer-info-button");
    private final By successNotification = By.cssSelector("div.bar-notification.success");
    private final By ordersLink        = By.cssSelector("a[href='/order/history']");
    private final By addressesLink     = By.cssSelector("a[href='/customer/addresses']");
    private final By changePasswordLink = By.cssSelector("a[href='/customer/changepassword']");

    // Change password fields
    private final By oldPasswordField  = By.id("OldPassword");
    private final By newPasswordField  = By.id("NewPassword");
    private final By confirmNewPassword = By.id("ConfirmNewPassword");
    private final By changePasswordBtn = By.cssSelector("button.change-password-button");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    /** Update the first name on the account info form. */
    public void updateFirstName(String newFirstName) {
        WebElement field = waitForVisible(firstNameField);
        field.clear();
        field.sendKeys(newFirstName);
    }

    /** Update the last name on the account info form. */
    public void updateLastName(String newLastName) {
        WebElement field = waitForVisible(lastNameField);
        field.clear();
        field.sendKeys(newLastName);
    }

    /** Get the current email shown on the account page. */
    public String getEmail() {
        return waitForVisible(emailField).getAttribute("value");
    }

    /** Save the account info form. */
    public void saveAccountInfo() {
        scrollToElement(waitForVisible(saveButton));
        waitForClickable(saveButton).click();
    }

    /** Check if the success notification is displayed after saving. */
    public boolean isSuccessNotificationDisplayed() {
        return !driver.findElements(successNotification).isEmpty();
    }

    /** Navigate to order history. */
    public void goToOrders() {
        waitForClickable(ordersLink).click();
    }

    /** Navigate to addresses. */
    public void goToAddresses() {
        waitForClickable(addressesLink).click();
    }

    /** Navigate to change password page. */
    public void goToChangePassword() {
        waitForClickable(changePasswordLink).click();
    }
}
