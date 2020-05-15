package com.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.framework.base.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class TestListeners implements ITestListener {
    Reporting reporting = new Reporting();
    private ExtentReports extentReports = reporting.createReportInstance();
    private ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<ExtentTest>();

    public void onTestStart(ITestResult iTestResult) {
        ExtentTest test = extentReports.createTest(iTestResult.getTestClass().getName() +
                "::" + iTestResult.getMethod().getMethodName());
        extentTestThreadLocal.set(test);
    }

    public void onTestSuccess(ITestResult iTestResult) {
        extentTestThreadLocal.get().log(Status.PASS, iTestResult.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult iTestResult) {
        String screenshotPath = null;
        try {
            screenshotPath = reporting.captureScreenshot(DriverFactory.getDriver(),
                    iTestResult.getMethod().getMethodName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            extentTestThreadLocal.get().addScreenCaptureFromPath(screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extentTestThreadLocal.get().log(Status.FAIL, iTestResult.getMethod().getMethodName());
        extentTestThreadLocal.get().log(Status.FAIL, iTestResult.getThrowable());
    }

    public void onTestSkipped(ITestResult iTestResult) {
        extentTestThreadLocal.get().log(Status.SKIP, iTestResult.getMethod().getMethodName());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
