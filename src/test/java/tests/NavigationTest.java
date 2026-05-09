package tests;

import base.BaseTest;
import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for site navigation, static pages, browser history,
 * and multiple page verification on demo.nopcommerce.com.
 */
public class NavigationTest extends BaseTest {

    @Test(description = "Verify home page title and key elements are present")
    public void verifyHomePageStaticContent() {
        driver.get(ConfigReader.getBaseUrl());

        // Static page test — verify text and element presence
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("nopCommerce"),
                "Home page title should contain 'nopCommerce', got: " + title);

        // Verify main navigation categories exist
        WebElement computersLink = driver.findElement(
                By.xpath("//ul[contains(@class,'top-menu')]//a[contains(text(),'Computers')]"));
        Assert.assertTrue(computersLink.isDisplayed(),
                "Computers navigation link should be visible");
    }

    @Test(description = "Iterate over multiple category pages and verify each has a title")
    public void verifyMultipleCategoryPages() {
        // Multiple page test — array of URLs + loop
        List<String> categoryUrls = Arrays.asList(
                ConfigReader.getBaseUrl() + "/computers",
                ConfigReader.getBaseUrl() + "/electronics",
                ConfigReader.getBaseUrl() + "/apparel",
                ConfigReader.getBaseUrl() + "/digital-downloads",
                ConfigReader.getBaseUrl() + "/books"
        );

        for (String url : categoryUrls) {
            driver.get(url);
            String pageTitle = driver.getTitle();
            Assert.assertFalse(pageTitle.isEmpty(),
                    "Page title should not be empty for URL: " + url);
            Assert.assertTrue(driver.getCurrentUrl().contains(
                    url.replace(ConfigReader.getBaseUrl(), "")),
                    "Should navigate to correct category page: " + url);
        }
    }

    @Test(description = "Test browser back and forward navigation history")
    public void testBrowserHistoryNavigation() {
        driver.get(ConfigReader.getBaseUrl());
        String homeUrl = driver.getCurrentUrl();

        // Navigate to computers page
        driver.get(ConfigReader.getBaseUrl() + "/computers");
        String computersUrl = driver.getCurrentUrl();
        Assert.assertTrue(computersUrl.contains("computers"),
                "Should be on computers page");

        // Go back to home
        driver.navigate().back();
        Assert.assertEquals(driver.getCurrentUrl(), homeUrl,
                "Back navigation should return to home page");

        // Go forward to computers
        driver.navigate().forward();
        Assert.assertTrue(driver.getCurrentUrl().contains("computers"),
                "Forward navigation should return to computers page");
    }

    @Test(description = "Verify page title using getTitle() method")
    public void verifyPageTitleUsingGetTitle() {
        driver.get(ConfigReader.getBaseUrl() + "/computers");
        HomePage homePage = new HomePage(driver);

        String title = homePage.getPageTitle();
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
        Assert.assertTrue(title.length() > 0, "Page title should have content");
    }

    @Test(description = "Use complex XPath to find elements in navigation menu")
    public void findElementsUsingComplexXPath() {
        driver.get(ConfigReader.getBaseUrl());

        // Complex XPath 1: find submenu links under Computers
        List<WebElement> subMenuLinks = driver.findElements(
                By.xpath("//ul[contains(@class,'top-menu')]//li[contains(@class,'has-sublist')]//ul//a"));
        Assert.assertFalse(subMenuLinks.isEmpty(),
                "Should find submenu links using complex XPath");

        // Complex XPath 2: find footer links containing specific href
        List<WebElement> footerLinks = driver.findElements(
                By.xpath("//div[@class='footer']//a[contains(@href,'/')]"));
        Assert.assertFalse(footerLinks.isEmpty(),
                "Should find footer links using complex XPath");

        // Complex XPath 3: find product boxes on featured section
        List<WebElement> featuredProducts = driver.findElements(
                By.xpath("//div[contains(@class,'product-grid')]//div[contains(@class,'product-item')]"));
        System.out.println("Featured products found: " + featuredProducts.size());
    }

    @Test(description = "Scroll to footer using JavascriptExecutor")
    public void scrollToFooterUsingJavascriptExecutor() {
        driver.get(ConfigReader.getBaseUrl());

        // JavascriptExecutor task — scroll to bottom of page
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");

        WebElement footer = driver.findElement(By.cssSelector("div.footer"));
        Assert.assertTrue(footer.isDisplayed(), "Footer should be visible after scrolling");
    }

    @Test(description = "Verify cookie is set after visiting the site")
    public void verifyCookieManipulation() {
        driver.get(ConfigReader.getBaseUrl());

        // Cookie manipulation task — read cookies
        java.util.Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();
        Assert.assertFalse(cookies.isEmpty(), "Site should set cookies on visit");

        // Add a custom cookie
        driver.manage().addCookie(
                new org.openqa.selenium.Cookie("test_cookie", "selenium_test"));

        // Read back the custom cookie
        org.openqa.selenium.Cookie testCookie = driver.manage().getCookieNamed("test_cookie");
        Assert.assertNotNull(testCookie, "Custom cookie should be readable");
        Assert.assertEquals(testCookie.getValue(), "selenium_test",
                "Cookie value should match what was set");

        // Delete the custom cookie
        driver.manage().deleteCookieNamed("test_cookie");
        Assert.assertNull(driver.manage().getCookieNamed("test_cookie"),
                "Cookie should be deleted");
    }
}
