package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ElectronicsPage  extends  BasePage {

    By samsung = By.cssSelector("a[href^='/egypt-en/samsung/']");


    public ElectronicsPage(WebDriver driver) {
        super(driver);
    }

    public  SamsungPage goToSamsungBrand() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(samsung)).click();
            wait.until(ExpectedConditions.urlContains("/egypt-en/samsung"));
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2000);");
            new Actions(driver).sendKeys(Keys.ESCAPE).perform();
            new Actions(driver).moveToElement(driver.findElement(samsung)).perform();
            wait.until(ExpectedConditions.elementToBeClickable(samsung)).click();
            wait.until(ExpectedConditions.urlContains("/egypt-en/samsung"));

        }
        return new SamsungPage(driver);
    }

}
