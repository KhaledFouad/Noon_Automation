package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class VerifyingItems extends BasePage {
  private  final By itemCard =By.xpath("//h2[@data-qa='plp-product-box-name']/ancestor::div[contains(@class,'ProductBoxVertical')][1]");
  private final  By itemPrice =By.xpath(".//div[@data-qa='plp-product-box-price']//div[contains(@class,'sellingPrice')]//strong");
  private  final  By itemRating =By.cssSelector("div[class*='RatingPreviewStar'][class*='textCtr']");

    public VerifyingItems(WebDriver driver) {
        super(driver);
    }

    public boolean  verifyItemDetails(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(itemCard));

       for ( int i=0 ; i < 5 ; i++) {
           try
           {
               Thread.sleep(1000);
               By cardLocator = By.xpath("(//h2[@data-qa='plp-product-box-name']/ancestor::div[contains(@class,'ProductBoxVertical')])[" + (i + 1) + "]");
               WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(cardLocator));
               wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(card, itemPrice));        String price = card.findElement(itemPrice).getText();
               String rating = card.findElement(itemRating).getText();
               System.out.println("Price: " + price);
               System.out.println("Rating: " + rating);

       } catch (InterruptedException e) {
               throw new RuntimeException ( e );

           }

}
         return true;
    }
}
