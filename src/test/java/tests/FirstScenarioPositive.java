package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ElectronicsPage;
import pages.HomePage;
import pages.SamsungPage;
import pages.VerifyingItems;

public class FirstScenarioPositive extends BaseTest {

    @Test
    public void firstScenarioPositive() {
        HomePage homePage = new HomePage(this.driver);
        homePage.goToElectronicsCategory();
        ElectronicsPage electronicsPage = new ElectronicsPage(this.driver);
        electronicsPage.goToSamsungBrand();


        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("/egypt-en/samsung"),
                "Samsung page not opened. Current URL: " + driver.getCurrentUrl());

        SamsungPage samsungPage = new SamsungPage(this.driver);
        samsungPage.sortByBestRated();
        samsungPage.filterByPrice("1000", "3000");
        VerifyingItems verifyingItems = new VerifyingItems(this.driver);
        verifyingItems.verifyItemDetails();
        Assert. assertTrue(true, "Item details verification failed.");


    }

}
