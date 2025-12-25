package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SamsungPage extends  BasePage {
    private final By sortingFilter = By.xpath("//span[text()='Sort By']");
    private final By bestRatedButton = By.xpath("//a[text()='Best Rated']");
    private final By priceFilter = By.xpath("//h3[text()='Price']");
    private final By priceMinButton =  By.name("min");
    private final By priceMaxButton = By.name("max");

    public SamsungPage(WebDriver driver) {
        super(driver);
    }
  public void sortByBestRated(){
wait.until( ExpectedConditions.elementToBeClickable(sortingFilter)).click();
wait.until( ExpectedConditions.elementToBeClickable(bestRatedButton)).click();  ;

  }

    public void filterByPrice(String minPrice , String maxPrice) {
        wait.until(ExpectedConditions.elementToBeClickable(priceFilter)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceMinButton)).clear();
        driver.findElement(priceMinButton).sendKeys( minPrice);
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceMaxButton)).clear();
        driver.findElement(priceMaxButton).sendKeys( maxPrice);
        driver.findElement(priceMaxButton).submit();
 }
}
