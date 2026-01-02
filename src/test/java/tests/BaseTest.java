package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.EXcelFileManager;
import utils.JsonFileManager;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    WebDriver driver;
    public EXcelFileManager excelFileManager;
    public JsonFileManager jsonFileManager;
    ConfigLoader configLoader = new ConfigLoader("src/main/resources/testData/Providers.properties");
    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--password-store=basic");
        excelFileManager = new EXcelFileManager("src/main/resources/testData/Providerss.xlsx", "KhaledSheet");
        jsonFileManager = new JsonFileManager("src/main/resources/testData/Provider.json");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(configLoader.getProperty("url")  );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}