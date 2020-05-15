package com.framework.base;

import com.framework.utils.ReadConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.*;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory extends ReadConfig {
    public WebDriver driver;
    public Logger logger;

    protected static Map<String, WebDriver> webDriverMap = new HashMap();

    public DriverFactory() {
    }

    public static synchronized WebDriver getDriver() {
        Long threadId = Thread.currentThread().getId();
        return (WebDriver)webDriverMap.get(threadId + "");
    }

    public WebDriver getBrowser(String browserType) {

        if (browserType.equalsIgnoreCase("chrome")) {
            //setup the chromedriver using WebDriverManager
            WebDriverManager.chromedriver().setup();
            //Create driver object for Chrome
            driver = new ChromeDriver();
        } else if (browserType.equalsIgnoreCase("edge")) {
            //setup the EDGE Chromium driver using WebDriverManager
            WebDriverManager.edgedriver().setup();
            //Create driver object for EDGE Chromium
            driver = new EdgeDriver();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            //setup the firefoxdriver using WebDriverManager
            WebDriverManager.firefoxdriver().setup();
            //Create driver object for Firefox
            driver = new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase("ie")) {
            //setup the ie driver using WebDriverManager
            WebDriverManager.iedriver().setup();
            //Create driver object for Chrome
            driver = new InternetExplorerDriver();
        } else {
            System.out.println("Failed to initialize the WEBDRIVER");
        }
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeTest
    public void OpenBrowser() {
        logger = Logger.getLogger("Excel In Testing");
        PropertyConfigurator.configure("log4j.properties");

        getBrowser(getBrowserName());
    }

    @AfterTest
    public void quitBrowser() {
        driver.quit();
    }

    public WebDriver driver() {
        return this.getDriver();
    }
}
