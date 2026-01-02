package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {


    private final By productBoxes = By.xpath("//div[contains(@class,'ProductBoxVertical')]");
    private final By productLinkInBox = By.xpath(".//a[contains(@href,'/p/')][1]");
    private final By productNameInBox = By.xpath(".//h2[@data-qa='plp-product-box-name']");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

  public List<String> getProductLinks(int[] positions) {
      List<String> links = new ArrayList<>();
      for (int pos : positions) {
          if (pos <= 0) continue;
          links.add(getProductLinkAt(pos));
      }
      return links;
  }

    private String getProductLinkAt(int position) {
        List<String> links = new ArrayList<>();
        List<org.openqa.selenium.WebElement> boxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productBoxes));
        if (position <= boxes.size()) {
            org.openqa.selenium.WebElement box = boxes.get(position - 1);
            try {
                org.openqa.selenium.WebElement linkEl = box.findElement(productLinkInBox);
                return linkEl.getAttribute("href");
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public  List<String> getFirstNUniqueProductLinks(int n) {
        List<String> links = new ArrayList<>();
        List<org.openqa.selenium.WebElement> boxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productBoxes));

        for (org.openqa.selenium.WebElement box : boxes) {
            if (links.size() >= n) break;
            try {
                org.openqa.selenium.WebElement linkEl = box.findElement(productLinkInBox);
                String href = linkEl.getAttribute("href");
                if (href != null && !href.isBlank() && !links.contains(href)) {
                    links.add(href);
                }
            } catch (Exception ignored) {
            }
        }

        return links;
    }

}
