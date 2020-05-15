package com.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.base.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Reporting extends DriverFactory {
    private Map<String, ExtentTest> extentTestMap;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;
    public String timeStamp;

    ReadConfig readConfig = new ReadConfig();

    public ExtentReports createReportInstance() {
        timeStamp = null;
        //time stamp
        timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String repName = "Automation Report_" + timeStamp + ".html";
        //specify location of the report
        htmlReporter = new ExtentHtmlReporter(
                System.getProperty("user.dir") + "/Results/" + timeStamp + "/" + repName);

        htmlReporter.config().setDocumentTitle("Excel In Testing Project");
        htmlReporter.config().setReportName("Test Automation Report");
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host name", "localhost");
        extent.setSystemInfo("Environment", readConfig.getEnvironment());
        return extent;
    }

    public String captureScreenshot(WebDriver driver, String ScreenshotName) throws IOException {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String destination = System.getProperty(
                "user.dir") + "/Results/" + timeStamp + "/Screenshots/" + ScreenshotName + date + ".png";
        TakesScreenshot tks = (TakesScreenshot) driver;
        File src = tks.getScreenshotAs(OutputType.FILE);
        try {
            File finalDestination = new File(destination);
            FileUtils.copyFile(src, finalDestination);
        } catch (IOException io) {
            System.out.println("Unable to capture screenshot " + io.getMessage());
        }
        return destination;
    }

    private String getScreenShotHyperLink(WebDriver webDriver) {
        StringBuilder builder = new StringBuilder();

        try {
            String screenshotFilePath = this.captureScreenshot(webDriver, this.getClass().getName());
            builder.append("<a href='" + screenshotFilePath + "' target='_blank'>Click here</a>");
        } catch (Exception e) {
            builder.append("<p>Could not capture screenshot due to <br/>" + e.getMessage() + "</p>");
        }

        return builder.toString();
    }
    
}
