package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the nopCommerce search results page.
 */
public class SearchResultsPage extends BasePage {

    private final By searchResults     = By.cssSelector("div.product-item");
    private final By noResultsMessage  = By.cssSelector("div.no-result");
    private final By searchInput       = By.id("q");
    private final By searchButton      = By.cssSelector("button.search-button");
    private final By productTitles     = By.cssSelector("h2.product-title a");
    private final By advancedSearchCheckbox = By.id("advs");
    private final By categoryDropdown  = By.id("cid");
    private final By priceFromField    = By.id("pf");
    private final By priceToField      = By.id("pt");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    /** Get the number of search results. */
    public int getResultCount() {
        return driver.findElements(searchResults).size();
    }

    /** Check if no-results message is displayed. */
    public boolean hasNoResults() {
        return !driver.findElements(noResultsMessage).isEmpty();
    }

    /** Click on the first search result. */
    public ProductPage clickFirstResult() {
        List<WebElement> titles = driver.findElements(productTitles);
        if (!titles.isEmpty()) {
            titles.get(0).click();
        }
        return new ProductPage(driver);
    }

    /** Get the text of the first product title in results. */
    public String getFirstResultTitle() {
        List<WebElement> titles = driver.findElements(productTitles);
        if (!titles.isEmpty()) {
            return titles.get(0).getText();
        }
        return "";
    }

    /** Perform a new search from the search results page. */
    public void searchFor(String keyword) {
        WebElement input = waitForVisible(searchInput);
        input.clear();
        input.sendKeys(keyword);
        waitForClickable(searchButton).click();
    }

    /** Check if URL contains the search query. */
    public boolean urlContainsQuery(String query) {
        return driver.getCurrentUrl().contains(query);
    }
}
