package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object for the nopCommerce registration page.
 */
public class RegisterPage extends BasePage {

    private final By maleRadio         = By.id("gender-male");
    private final By femaleRadio       = By.id("gender-female");
    private final By firstNameField    = By.id("FirstName");
    private final By lastNameField     = By.id("LastName");
    private final By dobDayDropdown    = By.name("DateOfBirthDay");
    private final By dobMonthDropdown  = By.name("DateOfBirthMonth");
    private final By dobYearDropdown   = By.name("DateOfBirthYear");
    private final By emailField        = By.id("Email");
    private final By newsletterCheckbox = By.id("Newsletter");
    private final By passwordField     = By.id("Password");
    private final By confirmPassword   = By.id("ConfirmPassword");
    private final By registerButton    = By.id("register-button");
    private final By successMessage    = By.cssSelector("div.result");
    private final By errorSummary      = By.cssSelector("div.message-error");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /** Fill the complete registration form and submit. */
    public void registerUser(String firstName, String lastName,
                              String email, String password) {
        // Gender radio button
        waitForClickable(maleRadio).click();

        // First and last name
        WebElement first = waitForVisible(firstNameField);
        first.clear();
        first.sendKeys(firstName);

        WebElement last = waitForVisible(lastNameField);
        last.clear();
        last.sendKeys(lastName);

        // Date of birth dropdowns
        new Select(waitForVisible(dobDayDropdown)).selectByValue("15");
        new Select(waitForVisible(dobMonthDropdown)).selectByValue("6");
        new Select(waitForVisible(dobYearDropdown)).selectByValue("1995");

        // Email
        WebElement emailInput = waitForVisible(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);

        // Newsletter checkbox — ensure it is checked
        WebElement newsletter = waitForVisible(newsletterCheckbox);
        if (!newsletter.isSelected()) {
            newsletter.click();
        }

        // Password
        WebElement pw = waitForVisible(passwordField);
        pw.clear();
        pw.sendKeys(password);

        WebElement cpw = waitForVisible(confirmPassword);
        cpw.clear();
        cpw.sendKeys(password);

        // Submit
        waitForClickable(registerButton).click();
    }

    /** Check if registration succeeded. */
    public boolean isRegistrationSuccessful() {
        return waitForVisible(successMessage).getText().contains("Your registration completed");
    }

    /** Get error message text. */
    public String getErrorMessage() {
        return waitForVisible(errorSummary).getText();
    }

    /** Select gender: "male" or "female". */
    public void selectGender(String gender) {
        if ("female".equalsIgnoreCase(gender)) {
            waitForClickable(femaleRadio).click();
        } else {
            waitForClickable(maleRadio).click();
        }
    }
}
