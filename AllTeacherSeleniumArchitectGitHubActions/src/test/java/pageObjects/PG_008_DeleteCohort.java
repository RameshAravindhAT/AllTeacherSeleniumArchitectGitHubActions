package pageObjects;

import java.util.List;

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

public class PG_008_DeleteCohort extends BaseClass {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public PG_008_DeleteCohort(WebDriver driver) {
        this.driver.set(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//p)[1]")
    public WebElement CohortInfopage;

    @FindBy(xpath = "//div[@role='presentation']//div[2]")
    public WebElement toastMessage;

    @FindBy(xpath = "((//div)[5]/div)[2]")
    public List<WebElement> lastupdatehistory;

    public PG_008_DeleteCohort Choose_the_prompts_and_verify_the_toastMessage(String PromptActions, String cohortExists) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Wait for the prompt button to be visible and clickable
            WebElement actualPrompt = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'MuiDialogContent-root')]/p//following::button[text()='" + PromptActions + "']")));

            if (actualPrompt.getText().equalsIgnoreCase("Yes")) {
                // Click on the Yes button to confirm the deletion
                TestContext.getJsExecutor().executeScript("arguments[0].click();", actualPrompt);
                Thread.sleep(1000);  // Wait for the deletion to complete
                
                // Verify the toast message
                String actualToast = TestContext.getWait().until(ExpectedConditions.visibilityOf(toastMessage)).getText();
                Assert.assertEquals(actualToast, "Cohort deleted successfully");

                // Verify the cohort is no longer present in the list
                TestContext.getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//p[text()='My cohorts']//following::div[5]//div[*]/p)[text()='" + cohortExists + "']")));
                
                // Report the success
                ExtentReportManager.reportStep(methodName + " " + PromptActions, "PASS: Cohort is successfully deleted");
                TestContext.getLogger().info("Successfully clicked on the " + PromptActions + " button for cohort deletion.");
            } else if (actualPrompt.getText().equalsIgnoreCase("No")) {
                // Click on the No button to cancel the deletion
                TestContext.getJsExecutor().executeScript("arguments[0].click();", actualPrompt);
                Thread.sleep(3000);  // Wait for cancellation to take effect
                
                // Verify that the cohort still exists
                WebElement cohortPresent = driver.get().findElement(By.xpath("(//p[text()='My cohorts']//following::div[5]//div[*]/p)[text()='" + cohortExists + "']"));
                if (cohortPresent.getText().equalsIgnoreCase(cohortExists)) {
                    ExtentReportManager.reportStep(methodName + " " + PromptActions, "PASS: Cohort exists as expected.");
                }
            }
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName + " " + PromptActions, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click on " + PromptActions + " button for cohort deletion", e);
            e.printStackTrace();
        }

        return this;
    }
}
