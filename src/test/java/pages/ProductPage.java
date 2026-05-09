package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object for a nopCommerce product detail page.
 */
public class ProductPage extends BasePage {

    private final By productName       = By.cssSelector("div.product-name h1");
    private final By addToCartButton   = By.cssSelector("button.add-to-cart-button");
    private final By addToWishlistBtn  = By.cssSelector("button.add-to-wishlist-button");
    private final By productPrice      = By.cssSelector("span.price.actual-price");
    private final By quantityInput     = By.cssSelector("input.qty-input");
    private final By successNotification = By.cssSelector("div.bar-notification.success p");
    private final By reviewTab         = By.cssSelector("a[href*='productreviews']");

    // Review form
    private final By reviewTitleField  = By.id("AddNewReview_Title");
    private final By reviewTextField   = By.id("AddNewReview_ReviewText");
    private final By ratingGood        = By.cssSelector("input[id='addtab'][value='4']");
    private final By submitReviewBtn   = By.id("add-review");

    // Sort dropdown on category/list pages
    private final By sortByDropdown    = By.id("products-orderby");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    /** Get the product name from the detail page. */
    public String getProductName() {
        return waitForVisible(productName).getText();
    }

    /** Get the product price. */
    public String getProductPrice() {
        return waitForVisible(productPrice).getText();
    }

    /** Set quantity and add product to cart. */
    public void addToCart(int quantity) {
        WebElement qty = waitForVisible(quantityInput);
        qty.clear();
        qty.sendKeys(String.valueOf(quantity));
        waitForClickable(addToCartButton).click();
    }

    /** Add product to cart with default quantity. */
    public void addToCart() {
        waitForClickable(addToCartButton).click();
    }

    /** Add product to wishlist. */
    public void addToWishlist() {
        waitForClickable(addToWishlistBtn).click();
    }

    /** Get success notification text after adding to cart. */
    public String getSuccessNotificationText() {
        return waitForVisible(successNotification).getText();
    }

    /** Navigate to the product review tab. */
    public void goToReviewTab() {
        waitForClickable(reviewTab).click();
    }

    /** Submit a product review. */
    public void submitReview(String title, String reviewText) {
        WebElement titleField = waitForVisible(reviewTitleField);
        titleField.clear();
        titleField.sendKeys(title);

        WebElement textArea = waitForVisible(reviewTextField);
        textArea.clear();
        textArea.sendKeys(reviewText);

        // Select rating
        try {
            waitForClickable(ratingGood).click();
        } catch (Exception ignored) { }

        scrollToElement(waitForVisible(submitReviewBtn));
        waitForClickable(submitReviewBtn).click();
    }

    /** Select sort order on a product listing page. */
    public void sortBy(String visibleText) {
        Select sort = new Select(waitForVisible(sortByDropdown));
        sort.selectByVisibleText(visibleText);
    }
}
