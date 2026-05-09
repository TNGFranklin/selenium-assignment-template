package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the nopCommerce shopping cart page.
 */
public class CartPage extends BasePage {

    private final By cartItems         = By.cssSelector("tr.cart-item-row");
    private final By emptyCartMessage  = By.cssSelector("div.no-data");
    private final By updateCartButton  = By.id("updatecart");
    private final By continueShoppingBtn = By.cssSelector("button.continue-shopping-button");
    private final By checkoutButton    = By.cssSelector("button#checkout");
    private final By termsCheckbox     = By.id("termsofservice");
    private final By cartTotal         = By.cssSelector("span.product-subtotal");
    private final By removeButtons     = By.cssSelector("button.remove-btn");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /** Get the number of items in the cart. */
    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    /** Check if cart is empty. */
    public boolean isCartEmpty() {
        return !driver.findElements(emptyCartMessage).isEmpty();
    }

    /** Accept terms and proceed to checkout. */
    public void proceedToCheckout() {
        WebElement terms = waitForVisible(termsCheckbox);
        if (!terms.isSelected()) {
            terms.click();
        }
        waitForClickable(checkoutButton).click();
    }

    /** Click Continue Shopping button. */
    public void continueShopping() {
        waitForClickable(continueShoppingBtn).click();
    }

    /** Remove the first item from cart. */
    public void removeFirstItem() {
        java.util.List<WebElement> removeList = driver.findElements(removeButtons);
        if (!removeList.isEmpty()) {
            removeList.get(0).click();
        }
    }
}
