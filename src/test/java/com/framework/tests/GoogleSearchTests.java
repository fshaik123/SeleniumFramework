package com.framework.tests;

import com.framework.pages.HomePage;
import com.framework.utils.Reporting;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;


public class GoogleSearchTests extends Reporting {
    @Test
    public void GoogleSearchTest1() throws IOException {

        driver.navigate().to(getAppURL());
        HomePage homePage = new HomePage(driver);
        homePage.typeTextInTheSearchField("excel in testing");

        String title = driver.getTitle();
        //logger.createNode("Verify Title");
        Assert.assertEquals(title, "excel in testing - Google Search");
        homePage.getResultsCount();
        //reporting.failureReport(driver(), "Test", "Test Failed");
        //logger.createNode("Verify Results count is displayed");
    }
}
