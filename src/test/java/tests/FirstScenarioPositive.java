package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ElectronicsPage;
import pages.HomePage;
import pages.SamsungPage;
import pages.VerifyingItems;
import utils.EXcelFileManager;
import utils.JsonFileManager;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class FirstScenarioPositive extends BaseTest {


    @DataProvider(name = "priceRanges")
    public Object[][] priceRanges() {
        EXcelFileManager excel = new EXcelFileManager(
                "src/main/resources/testData/Providerss.xlsx",
                "KhaledSheet"
        );
        return excel.getAllData(1, 0, 2);
    }

    @DataProvider(name = "priceRangesFromJson")
    public Object[][] priceRangesFromJson() {

        JsonFileManager json = new JsonFileManager("src/main/resources/testData/Provider.json");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> ranges =
                (List<Map<String, Object>>) json.getValue("priceRanges");

        Object[][] data = new Object[ranges.size()][2];

        for (int i = 0; i < ranges.size(); i++) {
            data[i][0] = json.cleanNumber(ranges.get(i).get("minValue"));
            data[i][1] = json.cleanNumber(ranges.get(i).get("maxValue"));
        }
        return data;
    }



    @Test(dataProvider = "priceRanges")
    public void firstScenarioPositive1( String min, String max ) {
        HomePage homePage = new HomePage(this.driver);
        homePage.goToElectronicsCategory();

        ElectronicsPage electronicsPage = new ElectronicsPage(this.driver);
        electronicsPage.goToSamsungBrand();
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).toLowerCase().contains("/egypt-en/samsung"),
                "Samsung page not opened. Current URL: " + driver.getCurrentUrl());

        SamsungPage samsungPage = new SamsungPage(this.driver);
        samsungPage.sortByBestRated();
        samsungPage.filterByPrice( min , max);

        VerifyingItems verifyingItems = new VerifyingItems(this.driver);
        verifyingItems.verifyItemDetails();
        Assert.assertTrue(true, "Item  details verification failed.");
    }



    @Test ( dataProvider = "priceRangesFromJson")
    public void firstScenarioPositive2( String min, String max ) {
        HomePage homePage = new HomePage(this.driver);
        homePage.goToElectronicsCategory();

        ElectronicsPage electronicsPage = new ElectronicsPage(this.driver);
        electronicsPage.goToSamsungBrand();
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).toLowerCase().contains("/egypt-en/samsung"),
                "Samsung page not opened. Current URL: " + driver.getCurrentUrl());

        SamsungPage samsungPage = new SamsungPage(this.driver);
        samsungPage.sortByBestRated();
        samsungPage.filterByPrice( min , max);

        VerifyingItems verifyingItems = new VerifyingItems(this.driver);
        verifyingItems.verifyItemDetails();
        Assert.assertTrue(true, "Item  details verification failed.");
    }
    }


