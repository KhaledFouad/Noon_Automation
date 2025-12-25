package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(7));
    }
    WebElement loaded (By locator ){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    }
    void click (By locator){
        loaded(locator).click();
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

    }
    void  type (By locator, String text){
        WebElement el = loaded(locator);
        el.clear();
        el.sendKeys(text);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }


}
