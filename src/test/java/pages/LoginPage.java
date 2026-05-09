package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the nopCommerce login page.
 */
public class LoginPage extends BasePage {

    private final By emailField        = By.id("Email");
    private final By passwordField     = By.id("Password");
    private final By loginButton       = By.cssSelector("button.login-button");
    private final By errorMessage      = By.cssSelector("div.message-error");
    private final By registerButton    = By.cssSelector("button.register-button");
    private final By rememberMeCheckbox = By.id("RememberMe");
    private final By forgotPasswordLink = By.cssSelector("a.forgot-password");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Fill in email and password, then click login. */
    public HomePage loginWith(String email, String password) {
        WebElement emailInput = waitForVisible(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);

        WebElement passwordInput = waitForVisible(passwordField);
        passwordInput.clear();
        passwordInput.sendKeys(password);

        waitForClickable(loginButton).click();
        return new HomePage(driver);
    }

    /** Fill in the email field only. */
    public void enterEmail(String email) {
        WebElement emailInput = waitForVisible(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    /** Fill in the password field only. */
    public void enterPassword(String password) {
        WebElement passwordInput = waitForVisible(passwordField);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /** Click the login button. */
    public void clickLoginButton() {
        waitForClickable(loginButton).click();
    }

    /** Check the Remember Me checkbox. */
    public void checkRememberMe() {
        WebElement checkbox = waitForVisible(rememberMeCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    /** Get the error message text shown after failed login. */
    public String getErrorMessage() {
        return waitForVisible(errorMessage).getText();
    }

    /** Check if error message is displayed. */
    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    /** Click the Forgot Password link. */
    public void clickForgotPassword() {
        waitForClickable(forgotPasswordLink).click();
    }

    /** Click Register button on the login page. */
    public RegisterPage clickRegister() {
        waitForClickable(registerButton).click();
        return new RegisterPage(driver);
    }
}
