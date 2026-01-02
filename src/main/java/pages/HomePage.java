package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private final By electronicsCategory = By.xpath("//span[text()='Electronics']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToElectronicsCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(electronicsCategory)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(electronicsCategory));
        new ElectronicsPage(driver);
    }
    public SearchPage searchFor(String keyword) {
        By searchInput = By.xpath("//*[@id=\"search-input\"]");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        wait.until(ExpectedConditions.elementToBeClickable(input));
        input.click();
        input.sendKeys(Keys.chord( Keys.CONTROL, "a" ), keyword);
        input.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.urlContains("search"));
        return new SearchPage(driver);
    }


}
