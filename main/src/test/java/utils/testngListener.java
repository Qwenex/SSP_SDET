package utils;

import base.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class testngListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();

        if (testClass instanceof BaseTest baseTest) {
            if (baseTest.webDriver != null) {
                AllureScreenshotUtils.attachScreenshot(baseTest.webDriver,
                        "Скриншот при падении: " + result.getName()
                );
            }
        }
    }
}
