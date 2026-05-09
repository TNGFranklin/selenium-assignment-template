package pages;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the nopCommerce home page.
 */
public class HomePage extends BasePage {

    private final By searchBox         = By.id("small-searchterms");
    private final By searchButton      = By.cssSelector("button.search-box-button");
    private final By loginLink         = By.cssSelector("a.ico-login");
    private final By registerLink      = By.cssSelector("a.ico-register");
    private final By logoutLink        = By.cssSelector("a.ico-logout");
    private final By accountLink       = By.cssSelector("a.ico-account");
    private final By cartLink          = By.cssSelector("a.ico-cart");
    private final By wishlistLink      = By.cssSelector("a.ico-wishlist");
    private final By logoLink          = By.cssSelector("div.header-logo a");
    private final By navComputersLink  = By.cssSelector("ul.top-menu a[href*='computers']");
    private final By newsletterEmail   = By.id("newsletter-email");
    private final By newsletterSubmit  = By.cssSelector("button.newsletter-subscribe-button");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /** Open the home page. */
    public void open() {
        driver.get(ConfigReader.getBaseUrl());
    }

    /** Search for a product. */
    public SearchResultsPage searchFor(String keyword) {
        WebElement box = waitForVisible(searchBox);
        box.clear();
        box.sendKeys(keyword);
        waitForClickable(searchButton).click();
        return new SearchResultsPage(driver);
    }

    /** Click the Login link in the header. */
    public LoginPage goToLogin() {
        waitForClickable(loginLink).click();
        return new LoginPage(driver);
    }

    /** Click the Register link in the header. */
    public RegisterPage goToRegister() {
        waitForClickable(registerLink).click();
        return new RegisterPage(driver);
    }

    /** Click the Logout link (only visible when logged in). */
    public void logout() {
        waitForClickable(logoutLink).click();
    }

    /** Click the My Account link. */
    public AccountPage goToAccount() {
        waitForClickable(accountLink).click();
        return new AccountPage(driver);
    }

    /** Check if the logout link is visible (i.e. user is logged in). */
    public boolean isLoggedIn() {
        return !driver.findElements(logoutLink).isEmpty();
    }

    /** Subscribe to newsletter with given email. */
    public void subscribeNewsletter(String email) {
        WebElement field = waitForVisible(newsletterEmail);
        field.clear();
        field.sendKeys(email);
        waitForClickable(newsletterSubmit).click();
    }

    /** Navigate to the Computers category. */
    public void goToComputers() {
        waitForClickable(navComputersLink).click();
    }

    /** Click the site logo to return to home. */
    public void clickLogo() {
        waitForClickable(logoLink).click();
    }
}
