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

public class PG_005_Cohorts extends BaseClass {
    
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public PG_005_Cohorts(WebDriver driver) {
        this.driver.set(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[contains(text(),'Class:')]/following::div[1]/span[1][text()='Class III']")
    public WebElement classDetails;

    @FindBy(xpath = "//p[text()='vowels']")
    public List<WebElement> cohortName;

    public PG_005_Cohorts View_the_CohortDetails(String CohortDetails) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Click on the view icon for the cohort
            TestContext.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[name()='svg'][@class='MuiSvgIcon-root MuiSvgIcon-colorPrimary MuiSvgIcon-fontSizeMedium css-kpzolb'])[2]"))).click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on the view button for the cohort card");
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click the view button", e);
        }
        return this;
    }

    public PG_005_Cohorts Verify_the_CohortDetails(String expectedCohortDetails) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            WebElement cohortPage = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[text()='Cohort detail']/following::div/div)[4]/p[1]")));
            String actualCohortName = cohortPage.getText().replace(":", "").trim();
            Assert.assertEquals(actualCohortName, expectedCohortDetails);
            ExtentReportManager.reportStep(methodName + " " + expectedCohortDetails, "PASS");
            TestContext.getLogger().info("Verified the cohort details successfully");
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to verify cohort details", e);
        }
        return this;
    }

    public PG_006_UpdateCohort Click_on_Edit_Button(String cohortSelection) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            String combinedXpath = "((((//p[text()='My cohorts']//following::div)[1]//p)//following::p[text()='" + cohortSelection + "']//following::div)[14]/button)[1]";
            WebElement button = TestContext.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath(combinedXpath)));
            button.click();
            Thread.sleep(2000);
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on edit button for cohort: " + cohortSelection);
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click edit button", e);
        }
        return new PG_006_UpdateCohort(driver.get());
    }

    public PG_007_CohortHistory Selection_of_Cohort_to_ViewHistory(String cohortSelection) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            String combinedXpath = "((((//p[text()='My cohorts']//following::div)[1]//p)//following::p[text()='" + cohortSelection + "']//following::div)[14]/button)[4]";
            WebElement button = TestContext.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath(combinedXpath)));
            button.click();
            Thread.sleep(2000);
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on view history button for cohort: " + cohortSelection);
        } catch (Exception e) {
            TestContext.getLogger().error("Failed to click on view history button", e);
        }
        return new PG_007_CohortHistory(driver.get());
    }

    public PG_008_DeleteCohort Click_Delete_to_RemoveCohort(String cohortSelection) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            String combinedXpath = "((((//p[text()='My cohorts']//following::div)[1]//p)//following::p[text()='" + cohortSelection + "']//following::div)[14]/button)[3]";
            WebElement button = TestContext.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath(combinedXpath)));
            button.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on delete button for cohort: " + cohortSelection);
        } catch (Exception e) {
            TestContext.getLogger().error("Failed to click delete button", e);
        }
        return new PG_008_DeleteCohort(driver.get());
    }
}
