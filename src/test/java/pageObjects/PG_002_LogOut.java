package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import projectSpecifications.BaseClass;

public class PG_002_LogOut extends BaseClass {

    // Use ThreadLocal to ensure each thread has its own WebDriver instance
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Constructor
    public PG_002_LogOut(WebDriver driver) {
        this.driver.set(driver); // Use ThreadLocal to set the driver
        PageFactory.initElements(driver, this); // Initialize page elements
    }

    // Click on the prompt (confirm logout action, for example)
    public PG_002_LogOut Click_On_Prompt(String promptAction) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            driver.get().findElement(By.xpath("//button[text()='" + promptAction + "']")).click();
            reportStep(methodName + " " + promptAction, "pass");
            logger.info(methodName + " is clicked");
        } catch (Exception e) {
            logger.error(methodName + " " + promptAction + " is not clicked");
            e.printStackTrace();
        }
        return this;
    }

    // Verify the URL after logging out (e.g., checking if the user was redirected to login page)
    public PG_002_LogOut verify_the_Results(String expectedURL) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            String currentUrl = driver.get().getCurrentUrl();
            Assert.assertEquals(currentUrl, expectedURL);
            reportStep(methodName + " " + expectedURL, "pass");
            logger.info(methodName + " URL is verified");
        } catch (Exception e) {
            logger.error(methodName + " " + expectedURL + " is not verified");
            e.printStackTrace();
        }
        return this;
    }
}
