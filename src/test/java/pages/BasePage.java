package pages;

import config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage provides shared utilities for all page classes.
 * Every page class extends this to avoid code duplication.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    /** Wait for element to be visible and return it. */
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait for element to be clickable and return it. */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Wait for element to be present in DOM and return it. */
    protected WebElement waitForPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /** Scroll to an element using JavascriptExecutor. */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /** Click an element using JavascriptExecutor (for hidden/overlapped elements). */
    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /** Get current page title. */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /** Get current page URL. */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Navigate to a URL. */
    public void navigateTo(String url) {
        driver.get(url);
    }
}
