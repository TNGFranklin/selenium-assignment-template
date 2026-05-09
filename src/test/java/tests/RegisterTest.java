package tests;

import base.BaseTest;
import config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.RandomDataGenerator;

/**
 * Tests for user registration on demo.nopcommerce.com.
 * Covers: registration form, radio buttons, dropdowns, checkboxes.
 */
public class RegisterTest extends BaseTest {

    @Test(description = "Verify registration page title")
    public void verifyRegistrationPageTitle() {
        driver.get(ConfigReader.getBaseUrl() + "/register");
        RegisterPage page = new RegisterPage(driver);

        String title = page.getPageTitle();
        Assert.assertTrue(title.contains("Register"),
                "Page title should contain 'Register', got: " + title);
    }

    @Test(description = "Successfully register a new user with random data")
    public void registerNewUserSuccessfully() {
        // Random data generation (random_data task)
        String email     = RandomDataGenerator.generateEmail();
        String password  = RandomDataGenerator.generatePassword();
        String firstName = RandomDataGenerator.generateFirstName();
        String lastName  = RandomDataGenerator.generateLastName();

        driver.get(ConfigReader.getBaseUrl() + "/register");
        RegisterPage page = new RegisterPage(driver);

        // Fill form: gender radio, name fields, DOB dropdowns, email,
        // newsletter checkbox, password fields (covers fill_input, radio_button,
        // dropdown, send_form tasks)
        page.registerUser(firstName, lastName, email, password);

        Assert.assertTrue(page.isRegistrationSuccessful(),
                "Registration should complete successfully with valid data");
    }

    @Test(description = "Registration with empty form shows validation errors")
    public void registrationWithEmptyFormShowsErrors() {
        driver.get(ConfigReader.getBaseUrl() + "/register");
        RegisterPage page = new RegisterPage(driver);

        // Click register without filling anything
        org.openqa.selenium.WebElement btn =
                driver.findElement(org.openqa.selenium.By.id("register-button"));
        btn.click();

        // Should remain on register page
        Assert.assertTrue(driver.getCurrentUrl().contains("register"),
                "Should remain on registration page after empty form submission");
    }

    @Test(description = "Select female gender radio button on registration form")
    public void selectFemaleGenderRadioButton() {
        driver.get(ConfigReader.getBaseUrl() + "/register");
        RegisterPage page = new RegisterPage(driver);

        page.selectGender("female");

        org.openqa.selenium.WebElement femaleRadio =
                driver.findElement(org.openqa.selenium.By.id("gender-female"));
        Assert.assertTrue(femaleRadio.isSelected(),
                "Female radio button should be selected");
    }
}
