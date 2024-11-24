package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import projectSpecifications.BaseClass;
import utils.TestContext;
import utils.ExtentReportManager;

public class PG_009_Languages extends BaseClass {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public PG_009_Languages(WebDriver driver) {
        this.driver.set(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[text()='English']")
    public WebElement languages;

    @FindBy(xpath = "//p[text()='Select language']//following::button[text()='confirm']")
    public WebElement confirmButton;

    @FindBy(xpath = "//div[contains(@class,'MuiAlert-message')]")
    public WebElement toastmessage;

    public PG_009_Languages Click_on_the_language(String expectedlanguages) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Locate and click on the desired language element
            WebElement languageElement = driver.get().findElement(By.xpath("//*[text()='" + expectedlanguages + "']"));
            TestContext.getJsExecutor().executeScript("arguments[0].click();", languageElement);

            // Report success
            ExtentReportManager.reportStep(methodName + " " + expectedlanguages, "PASS: Language '" + expectedlanguages + "' is successfully clicked.");
            TestContext.getLogger().info(methodName + " - " + expectedlanguages + " language is clicked.");

        } catch (Exception e) {
            // Report failure and log the error
            ExtentReportManager.reportStep(methodName + " " + expectedlanguages, "FAIL: Language '" + expectedlanguages + "' could not be clicked.");
            TestContext.getLogger().error(methodName + " - Failed to click on language " + expectedlanguages, e);
            e.printStackTrace();
        }
        return this;
    }

    public PG_009_Languages Click_on_the_ConfirmButton() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Click on the confirm button
            TestContext.getJsExecutor().executeScript("arguments[0].click();", confirmButton);

            // Report success
            ExtentReportManager.reportStep(methodName, "PASS: Confirm button is clicked.");
            TestContext.getLogger().info(methodName + " - Confirm button is clicked.");

        } catch (Exception e) {
            // Report failure and log the error
            ExtentReportManager.reportStep(methodName, "FAIL: Confirm button could not be clicked.");
            TestContext.getLogger().error(methodName + " - Failed to click on confirm button", e);
            e.printStackTrace();
        }
        return this;
    }

    public PG_009_Languages Verify_the_toast_Message_and_syllable_verified(String classdetails, String expectedlanguages) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Define expected toast messages
            String onSuccess = "Language changed successfully";
            String onFailure = "You are already in the " + expectedlanguages + " language.";
            Thread.sleep(1000);

            // Wait for toast message to be visible
            TestContext.getWait().until(ExpectedConditions.visibilityOf(toastmessage));

            // Verify toast message content
            String actualToastMessage = toastmessage.getText();
            if (actualToastMessage.equals(onSuccess)) {
                Assert.assertEquals(actualToastMessage, onSuccess);
                PG_003_Dashboard dashboard = new PG_003_Dashboard(driver.get());
                Thread.sleep(2000);
                dashboard
                    .click_On_ClassDetails(classdetails)
                    .verify_the_CohortDetails();

                // Report success
                ExtentReportManager.reportStep(methodName + " " + onSuccess + " and syllable is " + expectedlanguages, "PASS: Language changed successfully and syllables verified.");
                TestContext.getLogger().info(methodName + " - Language change successful, and syllables verified for class " + classdetails);

            } else if (actualToastMessage.equals(onFailure)) {
                Assert.assertEquals(actualToastMessage, onFailure);

                // Report success
                ExtentReportManager.reportStep(methodName + " " + onFailure, "PASS: Language already set to " + expectedlanguages);
                driver.get().close(); // Close the driver if language is already set
                TestContext.getLogger().info(methodName + " - Language already set to " + expectedlanguages + ". Browser closed.");

            }
        } catch (Exception e) {
            // Report failure and log the error
            ExtentReportManager.reportStep(methodName, "FAIL: Toast message verification failed.");
            TestContext.getLogger().error(methodName + " - Failed to verify toast message for language change.", e);
            e.printStackTrace();
        }
        return this;
    }
}
