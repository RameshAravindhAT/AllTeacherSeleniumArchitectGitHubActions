package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.MediaEntityBuilder;

import projectSpecifications.BaseClass;

public class CustomTestListener extends BaseClass implements ITestListener {

    // ThreadLocal to store the method name for each test
    public static ThreadLocal<String> currentMethodName = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        currentMethodName.set(result.getMethod().getMethodName());
    }

    public static String getCurrentTestMethodName() {
        return currentMethodName.get();
    }

    public void onTestFinish(ITestResult result) {
        currentMethodName.remove();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logMethodExecution(result.getMethod().getMethodName(), "succeeded");
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().pass("Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Get method name and error message
        String methodName = result.getMethod().getMethodName().replace("_", " ");
        String errorMessage = result.getThrowable().getMessage();
        String detailedMessage = "Test failed: " + methodName + "\nError: " + errorMessage;

        // Take screenshot and store the path
        String screenshotPath = takeScreenshot(TestContext.getDriver());

        // Log failure in ExtentReport with the screenshot path
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().fail(detailedMessage,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }

        logMethodExecution(result.getMethod().getMethodName(), "failed");

       
    }

    public void logMethodExecution(String methodName, String status) {
        // Log the method execution status
        System.out.println("Method: " + methodName + " " + status);
    }

    public String takeScreenshot(WebDriver driver) {
        if (driver == null) {
            System.out.println("WebDriver is null, cannot take screenshot.");
            return "Error: WebDriver is null.";
        }

        // Create unique screenshot file name based on the current timestamp
        String uniqueScreenshotFileName = "screenshot_" + System.currentTimeMillis() + ".png";
        
        // Define the path where screenshots will be stored
        String uniqueFilePath = Paths.get("screenshots", uniqueScreenshotFileName).toAbsolutePath().toString();

        try {
            // Ensure the "screenshots" directory exists
            File screenshotDirectory = new File("screenshots");
            if (!screenshotDirectory.exists()) {
                screenshotDirectory.mkdirs();
            }

            // Take a screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Copy the screenshot to the desired location
            FileUtils.copyFile(screenshot, new File(uniqueFilePath));

            // Return the path of the screenshot
            return uniqueFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error while saving screenshot: " + e.getMessage();
        }
    }

  
}
