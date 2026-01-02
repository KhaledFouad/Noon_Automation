package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends  BasePage {

    private final By productName = By.cssSelector("h1[data-qa*='product-name'], h1");
    private final By productPrice = By.cssSelector("div[data-qa*='price'] strong, strong[data-qa*='price'], div[class*='sellingPrice'] strong");

    private final By addToCartButton = By.xpath(
            "//button[@data-qa='add-to-cart' or @data-qa='pdp-add-to-cart' or contains(translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'ADD TO CART') or .//*[contains(@alt,'add-to-cart')] or .//*[contains(@aria-label,'add-to-cart')]]"
    );
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        WebElement nameEl = wait.until(driver -> driver.findElement(productName));
        return nameEl.getText().trim();
    }
    public String getProductPrice() {
        WebElement priceEl = wait.until(driver -> driver.findElement(productPrice));
        return priceEl.getText().trim();
    }
        public void addToCart() {
            WebElement addButton = wait.until(driver -> driver.findElement(addToCartButton));
            addButton.click();
        }
}
