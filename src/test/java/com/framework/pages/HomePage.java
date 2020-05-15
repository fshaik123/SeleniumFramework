package com.framework.pages;

import com.framework.base.DriverFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends DriverFactory {
    WebDriver driver;

    public HomePage(WebDriver getDriver) {
        driver = getDriver;
        PageFactory.initElements(getDriver, this);
    }

    @FindBy(name = "q")
    @CacheLookup
    WebElement txt_SearchField;

    public void typeTextInTheSearchField(String SearchText) {
        txt_SearchField.sendKeys(SearchText);
        txt_SearchField.sendKeys(Keys.ENTER);
    }

    @FindBy(id = "result-stats")
    WebElement txt_ResultsCount;
    public void getResultsCount() {
        txt_ResultsCount.isDisplayed();
    }
}
