package config;

import utils.logging.CustomReporter;
import utils.logging.EventHandler;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


/**
 * Base script functionality, can be used for all Selenium scripts.
 */
public class BaseConfig {
    private static EventFiringWebDriver driver;
    private static GeneralActions actions;

    /**
     * @param browser browser name
     * @return Driver instance of the supported browser
     */
    private WebDriver getDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty(
                        "webdriver.gecko.driver",
                        getResource("/geckodriver.exe"));
                return new FirefoxDriver();
            case "edge":
                System.setProperty(
                        "webdriver.edge.driver",
                        getResource("/MicrosoftWebDriver.exe"));
                return new InternetExplorerDriver();
            case "chrome":
                System.setProperty(
                        "webdriver.chrome.driver",
                        getResource("/chromedriver.exe"));
                return new ChromeDriver();
            default:
                throw new IllegalArgumentException("Unsupported " + browser + " browser.");
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }


    /**
     * @param resource The name of the resource
     * @return Path to resource
     */
    private String getResource(String resource) {
        try {
            return Paths.get(BaseConfig.class.getResource(resource).toURI()).toFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return resource;
    }

    /**
     * Prepares {@link WebDriver} instance with timeout and browser window configurations.
     * Driver type is based on passed parameters to the automation project.
     */
    @BeforeClass
    @Parameters({"selenium.browser"})
    public void setUp(@Optional("chrome") String browser) {
        driver = new EventFiringWebDriver(getDriver(browser));
        driver.register(new EventHandler());

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(Config.getBaseUrl());

        actions = new GeneralActions(driver);
    }

    public static WebDriver getConiguredDriver(String browser) {
        return driver;
    }

    /**
     * Closes driver instance after test class execution and logging data.
     */
    @AfterClass
    @Parameters("selenium.browser")
    public void tearDown(@Optional("chrome") String browser) {
        if (driver != null) {
            driver.quit();
        }
        CustomReporter.logAction(browser);
        CustomReporter.writeLog(browser);
    }

}
