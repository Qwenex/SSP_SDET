package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private Integer retryCount = 0;
    private Integer maxRetry = 2;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < maxRetry) {
            retryCount++;
            return true;
        }
        return false;
    }
}
