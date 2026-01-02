package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

}
