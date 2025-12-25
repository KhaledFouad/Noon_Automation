package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
    private final By electronicsCategory = By.xpath("//span[text()='Electronics']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ElectronicsPage goToElectronicsCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(electronicsCategory)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(electronicsCategory));
        return new ElectronicsPage(driver);
    }




}
