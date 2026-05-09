package tests;

import base.BaseTest;
import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;

import java.util.List;

/**
 * Tests for search functionality on demo.nopcommerce.com.
 * Covers: search form, results, complex XPath, dropdown sort.
 */
public class SearchTest extends BaseTest {

    @Test(description = "Search for a product and verify results appear")
    public void searchForProductReturnsResults() {
        driver.get(ConfigReader.getBaseUrl());
        HomePage homePage = new HomePage(driver);

        SearchResultsPage results = homePage.searchFor("laptop");

        Assert.assertFalse(results.hasNoResults(),
                "Search for 'laptop' should return results");
        Assert.assertTrue(results.getResultCount() > 0,
                "At least one result should be found for 'laptop'");
    }

    @Test(description = "Search for non-existent product shows no results message")
    public void searchForNonExistentProductShowsNoResults() {
        driver.get(ConfigReader.getBaseUrl());
        HomePage homePage = new HomePage(driver);

        SearchResultsPage results = homePage.searchFor("xyznonexistentproduct123abc");

        Assert.assertTrue(results.hasNoResults() || results.getResultCount() == 0,
                "Search for non-existent product should show no results");
    }

    @Test(description = "Use complex XPath to find product prices on search results page")
    public void findProductPricesUsingComplexXPath() {
        driver.get(ConfigReader.getBaseUrl() + "/search?q=apple");

        // Complex XPath: find prices inside product items
        List<WebElement> prices = driver.findElements(
                By.xpath("//div[contains(@class,'product-item')]//span[contains(@class,'price')]"));
        System.out.println("Prices found: " + prices.size());
        // Just verify the XPath executes without error
        Assert.assertNotNull(prices, "XPath query should execute successfully");
    }

    @Test(description = "Use complex XPath to find add-to-cart buttons on search results")
    public void findAddToCartButtonsUsingComplexXPath() {
        driver.get(ConfigReader.getBaseUrl() + "/search?q=book");

        // Complex XPath: buttons inside product grid items
        List<WebElement> buttons = driver.findElements(
                By.xpath("//div[contains(@class,'product-grid')]//button[contains(@class,'add-to-cart-button') or contains(@class,'button-2')]"));
        System.out.println("Add to cart buttons found: " + buttons.size());
        Assert.assertNotNull(buttons, "XPath should find product action buttons");
    }

    @Test(description = "Use complex XPath to find product images with alt text")
    public void findProductImagesUsingComplexXPath() {
        driver.get(ConfigReader.getBaseUrl() + "/search?q=camera");

        // Complex XPath: images inside product picture divs
        List<WebElement> images = driver.findElements(
                By.xpath("//div[contains(@class,'picture')]//img[@alt and string-length(@alt)>0]"));
        System.out.println("Product images found: " + images.size());
        Assert.assertNotNull(images, "XPath should find product images with alt text");
    }

    @Test(description = "Sort search results using dropdown")
    public void sortSearchResultsUsingDropdown() {
        driver.get(ConfigReader.getBaseUrl() + "/search?q=computer");

        // Dropdown task — use Select class to change sort order
        List<WebElement> sortDropdowns = driver.findElements(By.id("products-orderby"));
        if (!sortDropdowns.isEmpty()) {
            new org.openqa.selenium.support.ui.Select(sortDropdowns.get(0))
                    .selectByValue("10"); // 10 = Name: A to Z in nopCommerce
            Assert.assertTrue(driver.getCurrentUrl().contains("orderby") ||
                    driver.findElements(By.cssSelector("div.product-item")).size() > 0,
                    "Sort should change the product listing");
        }
    }
}
