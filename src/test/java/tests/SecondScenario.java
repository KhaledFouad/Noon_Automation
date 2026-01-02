package tests;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.SearchPage;
import utils.EXcelFileManager;
import utils.JsonFileManager;
import utils.PropertiesFileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SecondScenario  extends  BaseTest {

    private static final Logger log = LoggerFactory.getLogger(SecondScenario.class);

    @DataProvider(name = "scenario2FromExcel")
    public Object[][] scenario2FromExcel() {
        EXcelFileManager excel = new EXcelFileManager(
                "src/main/resources/testData/Providerss.xlsx",
                "KhaledSheet"
        );
        return excel.getAllData(1, 0, 4);
    }

    @DataProvider(name = "scenario2FromJson")
    public Object[][] scenario2FromJson() {
        JsonFileManager json = new JsonFileManager("src/main/resources/testData/Provider.json");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> cases =
                (List<Map<String, Object>>) json.getValue("scenario2");

        Object[][] data = new Object[cases.size()][4];
        for (int i = 0; i < cases.size(); i++) {
            Map<String, Object> c = cases.get(i);
            data[i][0] = String.valueOf(c.get("searchTerm"));

            @SuppressWarnings("unchecked")
            List<Object> idx = (List<Object>) c.get("indexes");
            data[i][1] = json.cleanNumber(idx.get(0));
            data[i][2] = json.cleanNumber(idx.get(1));
            data[i][3] = json.cleanNumber(idx.get(2));
        }
        return data;
    }

    @DataProvider(name = "scenario2FromProperties")
    public Object[][] scenario2FromProperties() {
        PropertiesFileManager pf = new PropertiesFileManager("testData/Providers.properties");
        String term = pf.getProperty("SecondScenarioSearchTerm");
        String indexes = pf.getProperty("SecondScenarioIndexes");
        String[] parts = indexes.split(",");
        return new Object[][]{
                {term, parts[0].trim(), parts[1].trim(), parts[2].trim()}
        };
    }

    @Test(dataProvider = "scenario2FromExcel")
    public void testSecondScenarioFromExcel(String searchTerm, String index1, String index2, String index3) {

        HomePage homePage = new HomePage(driver);
        SearchPage resultsPage = homePage.searchFor(searchTerm);
        List<String> productLinks = resultsPage.getProductLinks(new int[]{
                Integer.parseInt(index1),
                Integer.parseInt(index2),
                Integer.parseInt(index3)
        });
        List<String> cartLinks = resultsPage.getProductLinks(new int[]{});
        List<String> expectednames = new ArrayList<>();
        List<String> expectedPrices = new ArrayList<>();
        for (String link : productLinks) {
            if (link != null && !link.isBlank()) {
                pages.ProductPage productPage = new pages.ProductPage(driver);
                driver.get(link);
                String name = productPage.getProductName();
                String price = productPage.getProductPrice();
                expectednames.add(name);
                expectedPrices.add(price);
                productPage.addToCart();
                log.info("Added to cart: " + name + " | Price: " + price);
            } else {
                log.warn("Invalid product link encountered, skipping.");
            }
        }
        CartPage cartPage = new CartPage(driver);
        cartPage.openCartItem(configLoader.getProperty("url"));
        List<pages.CartItem> cartItems = cartPage.getCartItem();
        for (int i = 0; i < cartItems.size(); i++) {
            pages.CartItem item = cartItems.get(i);

            String actualName = cartPage.safeText((WebElement) item, cartPage.itemName);
            String actualPrice = cartPage.safeText((WebElement) item, cartPage.itemPrice);

            int actualQty = cartPage.safeQuantity((WebElement) item);
            String expectedName = expectednames.get(i);
            String expectedPrice = expectedPrices.get(i);
            assert actualName.equals(expectedName) : "Expected name: " + expectedName + ", but found: " + actualName;
            assert actualPrice.equals(expectedPrice) : "Expected price: " + expectedPrice + ", but found: " + actualPrice;
            assert actualQty ==
                    1 : "Expected quantity: 1, but found: " + actualQty;

        }
    }

    @Test(dataProvider = "scenario2FromJson")
    public void testSecondScenarioFromJson(String searchTerm, String index1, String index2, String index3) {


        HomePage homePage = new HomePage(driver);
        SearchPage resultsPage = homePage.searchFor(searchTerm);
        List<String> productLinks = resultsPage.getProductLinks(new int[]{
                Integer.parseInt(index1),
                Integer.parseInt(index2),
                Integer.parseInt(index3)
        });
        List<String> cartLinks = resultsPage.getProductLinks(new int[]{});
        List<String> expectednames = new ArrayList<>();
        List<String> expectedPrices = new ArrayList<>();
        for (String link : productLinks) {
            if (link != null && !link.isBlank()) {
                pages.ProductPage productPage = new pages.ProductPage(driver);
                driver.get(link);
                String name = productPage.getProductName();
                String price = productPage.getProductPrice();
                expectednames.add(name);
                expectedPrices.add(price);
                productPage.addToCart();
                log.info("Added to cart: " + name + " | Price: " + price);
            } else {
                log.warn("Invalid product link encountered, skipping.");
            }
        }
        CartPage cartPage = new CartPage(driver);
        cartPage.openCartItem(configLoader.getProperty("url"));
        List<pages.CartItem> cartItems = cartPage.getCartItem();
        for (int i = 0; i < cartItems.size(); i++) {
            pages.CartItem item = cartItems.get(i);

            String actualName = cartPage.safeText((WebElement) item, cartPage.itemName);
            String actualPrice = cartPage.safeText((WebElement) item, cartPage.itemPrice);

            int actualQty = cartPage.safeQuantity((WebElement) item);
            String expectedName = expectednames.get(i);
            String expectedPrice = expectedPrices.get(i);
            assert actualName.equals(expectedName) : "Expected name: " + expectedName + ", but found: " + actualName;
            assert actualPrice.equals(expectedPrice) : "Expected price: " + expectedPrice + ", but found: " + actualPrice;
            assert actualQty ==
                    1 : "Expected quantity: 1, but found: " + actualQty;

        }
    }
    @Test(dataProvider = "scenario2FromProperties")
    public void testSecondScenarioFromProperties(String searchTerm, String index1, String index2
            , String index3) {
        HomePage homePage = new HomePage(driver);
        SearchPage resultsPage = homePage.searchFor(searchTerm);
        List<String> productLinks = resultsPage.getProductLinks(new int[]{
                Integer.parseInt(index1),
                Integer.parseInt(index2),
                Integer.parseInt(index3)
        });
        List<String> cartLinks = resultsPage.getProductLinks(new int[]{});
        List<String> expectednames = new ArrayList<>();
        List<String> expectedPrices = new ArrayList<>();

        for (String link : productLinks) {
            if (link != null && !link.isBlank()) {
                pages.ProductPage productPage = new pages.ProductPage(driver);
                driver.get(link);
                String name = productPage.getProductName();
                String price = productPage.getProductPrice();
                expectednames.add(name);
                expectedPrices.add(price);
                productPage.addToCart();
                log.info("Added to cart: " + name + " | Price: " + price);
            } else {
                log.warn("Invalid product link encountered, skipping.");
            }
        }
        CartPage cartPage = new CartPage(driver);
        cartPage.openCartItem(configLoader.getProperty("url"));
        List<pages.CartItem> cartItems = cartPage.getCartItem();
        for (int i = 0; i < cartItems.size(); i++) {
            pages.CartItem item = cartItems.get(i);

            String actualName = cartPage.safeText((WebElement) item, cartPage.itemName);
            String actualPrice = cartPage.safeText((WebElement) item, cartPage.itemPrice);

            int actualQty = cartPage.safeQuantity((WebElement) item);
            String expectedName = expectednames.get(i);
            String expectedPrice = expectedPrices.get(i);
            assert actualName.equals(expectedName) : "Expected name: " + expectedName + ", but found: " + actualName;
            assert actualPrice.equals(expectedPrice) : "Expected price: " + expectedPrice + ", but found: " + actualPrice;
            assert actualQty ==
                    1 : "Expected quantity: 1, but found: " + actualQty;

        }


    }



}
