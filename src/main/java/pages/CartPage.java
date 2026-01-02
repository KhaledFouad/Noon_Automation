package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage  extends BasePage {

    private final By cartItemRows = By.xpath(
            "//div[@data-qa='cart-item' or contains(@class,'CartItem') or contains(@class,'cart-item') or contains(@class,'cartItem')]"
    );
    public final By itemName = By.xpath(
            ".//a[contains(@href,'/p/')][1] | .//*[@data-qa='product-name'][1] | .//*[contains(@class,'itemName')][1]"
    );
    public final By itemPrice = By.xpath(
            ".//*[@data-qa='price' or contains(@data-qa,'price') or contains(@class,'price') or contains(@class,'sellingPrice')]//strong | .//strong[contains(.,'EGP') or contains(.,'AED') or contains(.,'SAR') or contains(.,'QAR')][1]"
    );

    private final By qtyInput = By.xpath(
            ".//input[@type='number' or contains(@name,'qty') or contains(@name,'quantity') or @data-qa='qty']"
    );
    public CartPage(WebDriver driver) {
        super(driver);
    }
    public void  openCartItem( String baseUrl) {

        String url = baseUrl;
        if (!url.endsWith("/")) url = url + "/";
        driver.get(url + "cart");
        wait.until(ExpectedConditions.urlContains("/cart"));

    }
    public List<CartItem> getCartItem() {

        List<CartItem> items = new java.util.ArrayList<>();

        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItemRows));
        } catch (Exception ignored) {
            return items;
        }

        List<WebElement> rows = driver.findElements(cartItemRows);
        for (WebElement row : rows) {
            String nameText = safeText(row, itemName);
            String priceText = safeText(row, itemPrice);
            int quantity = safeQuantity(row);
            if (!nameText.isBlank()) {
                items.add(new CartItem(nameText, priceText, quantity));
            }
        }
        return items;

    }


    public int safeQuantity(WebElement row) {
        try {
            WebElement qtyEl = row.findElement(qtyInput);
            String val = qtyEl.getAttribute("value");
            return Integer.parseInt(val);
        } catch (Exception e) {
            return 1;
        }
    }
    public  String safeText(WebElement row, By locator) {
        try {
            WebElement el = row.findElement(locator);
            return el.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }


}
