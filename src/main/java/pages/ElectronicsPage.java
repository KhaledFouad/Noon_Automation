package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ElectronicsPage  extends  BasePage {

    By samsung = By.cssSelector("a[href^='/egypt-en/samsung/']");


    public ElectronicsPage(WebDriver driver) {
        super(driver);
    }

    public  void goToSamsungBrand() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(samsung)).click();
            wait.until(ExpectedConditions.urlContains("/egypt-en/samsung"));
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2000);");
            wait.until(ExpectedConditions.elementToBeClickable(samsung)).click();
            wait.until(ExpectedConditions.urlContains("/egypt-en/samsung"));
        }
    }
}
