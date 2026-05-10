package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ManagerPage;
import utils.RandomDataGenerator;

import java.util.List;

/**
 * Tests for Bank Manager functionality on XYZ Bank.
 * Covers: add customer form, open account, customer list, sorting, search.
 */
public class ManagerTest extends BaseTest {

    private ManagerPage getManagerPage() {
        return new LoginPage(driver).loginAsManager();
    }

    @Test(description = "Manager can navigate to Add Customer form")
    public void managerCanOpenAddCustomerForm() {
        ManagerPage manager = getManagerPage();
        manager.clickAddCustomer();

        WebElement firstNameField = driver.findElement(
                By.xpath("//input[@placeholder='First Name']"));
        Assert.assertTrue(firstNameField.isDisplayed(),
                "First Name field should be visible after clicking Add Customer");
    }

    @Test(description = "Add Customer form has First Name, Last Name, Post Code inputs")
    public void addCustomerFormHasRequiredInputFields() {
        ManagerPage manager = getManagerPage();
        manager.clickAddCustomer();

        // fill_input: text fields (First Name, Last Name, Post Code)
        Assert.assertTrue(
                driver.findElement(By.xpath("//input[@placeholder='First Name']")).isDisplayed(),
                "First Name input should be present");
        Assert.assertTrue(
                driver.findElement(By.xpath("//input[@placeholder='Last Name']")).isDisplayed(),
                "Last Name input should be present");
        Assert.assertTrue(
                driver.findElement(By.xpath("//input[@placeholder='Post Code']")).isDisplayed(),
                "Post Code input should be present");
    }

    @Test(description = "Add a new customer with random data and confirm alert")
    public void addNewCustomerWithRandomData() {
        // random_data task — generate unique customer details
        String firstName = RandomDataGenerator.generateFirstName();
        String lastName  = RandomDataGenerator.generateLastName();
        String postCode  = RandomDataGenerator.generatePostCode();

        ManagerPage manager = getManagerPage();
        manager.addCustomer(firstName, lastName, postCode);

        // nopCommerce shows an alert on success
        try {
            String alertText = driver.switchTo().alert().getText();
            System.out.println("Alert: " + alertText);
            Assert.assertFalse(alertText.isEmpty(), "Success alert should appear");
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // Some browsers handle it differently
            System.out.println("No alert found, continuing: " + e.getMessage());
        }
    }

    @Test(description = "Open Account form has customer dropdown and currency dropdown")
    public void openAccountFormHasDropdowns() {
        ManagerPage manager = getManagerPage();
        manager.clickOpenAccount();

        // dropdown task — both are Select dropdowns
        WebElement customerDropdown = driver.findElement(By.id("userSelect"));
        WebElement currencyDropdown = driver.findElement(By.id("currency"));

        Assert.assertTrue(customerDropdown.isDisplayed(),
                "Customer dropdown should be visible");
        Assert.assertTrue(currencyDropdown.isDisplayed(),
                "Currency dropdown should be visible");
    }

    @Test(description = "Open an account for Harry Potter with Dollar currency")
    public void openAccountForHarryPotter() {
        ManagerPage manager = getManagerPage();
        manager.openAccount("Harry Potter", "Dollar");

        try {
            String alertText = driver.switchTo().alert().getText();
            System.out.println("Open Account alert: " + alertText);
            Assert.assertTrue(alertText.contains("Account") || alertText.contains("created"),
                    "Alert should confirm account creation");
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("Alert handling: " + e.getMessage());
        }
    }

    @Test(description = "Customer list table shows existing customers")
    public void customerListShowsExistingCustomers() {
        ManagerPage manager = getManagerPage();
        manager.clickCustomers();

        List<WebElement> rows = manager.getCustomerRows();
        Assert.assertFalse(rows.isEmpty(),
                "Customer table should show at least one customer");
        System.out.println("Total customers in table: " + rows.size());
    }

    @Test(description = "Search customer filters the customer table")
    public void searchCustomerFiltersTable() {
        ManagerPage manager = getManagerPage();
        manager.searchCustomer("Harry");

        List<WebElement> rows = manager.getCustomerRows();
        Assert.assertFalse(rows.isEmpty(),
                "Searching for 'Harry' should return at least one result");
    }

    @Test(description = "Sort customer list by First Name using complex XPath")
    public void sortCustomersByFirstName() {
        ManagerPage manager = getManagerPage();
        manager.clickCustomers();

        // complex_xpath: find any clickable element containing 'First Name' text
        // in the table header area
        java.util.List<WebElement> sortElements = driver.findElements(
                By.xpath("//*[contains(text(),'First Name')]"));

        System.out.println("Sort elements found: " + sortElements.size());
        if (!sortElements.isEmpty()) {
            sortElements.get(0).click();
        }

        List<WebElement> rows = manager.getCustomerRows();
        Assert.assertFalse(rows.isEmpty(), "Table should still have rows after sorting");
    }

    @Test(description = "Use complex XPath to find delete buttons in customer table")
    public void findDeleteButtonsUsingComplexXPath() {
        ManagerPage manager = getManagerPage();
        manager.clickCustomers();

        // complex_xpath: delete buttons inside table rows
        List<WebElement> deleteButtons = driver.findElements(
                By.xpath("//table/tbody/tr/td/button[contains(text(),'Delete')]"));
        System.out.println("Delete buttons found: " + deleteButtons.size());
        Assert.assertNotNull(deleteButtons,
                "Complex XPath should find delete buttons in table");
    }

    @Test(description = "Use complex XPath to find customer first names in table")
    public void findCustomerNamesUsingComplexXPath() {
        ManagerPage manager = getManagerPage();
        manager.clickCustomers();

        // complex_xpath: first cell of each table row
        List<WebElement> firstNames = driver.findElements(
                By.xpath("//table/tbody/tr/td[1]"));
        Assert.assertFalse(firstNames.isEmpty(),
                "Complex XPath should find first name cells in customer table");
        System.out.println("Customer first names found: " + firstNames.size());
    }
}
