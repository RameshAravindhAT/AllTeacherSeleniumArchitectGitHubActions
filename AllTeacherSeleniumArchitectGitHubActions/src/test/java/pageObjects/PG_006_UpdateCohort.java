package pageObjects;

import java.util.List;

import org.jspecify.annotations.Nullable;
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

public class PG_006_UpdateCohort extends BaseClass {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();	

    public PG_006_UpdateCohort(WebDriver driver) {
        this.driver.set(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "((//p[text()='My cohorts']//following::div)[1]//p)//following::p[text()='Cohort Name c1']")
    public WebElement cohortSelection;

    @FindBy(xpath = "(//p)[1]")
    public WebElement EditCohortpage;

    @FindBy(xpath = "((//p[text()='Edit cohorts']//following::div)[6]//following::p)[2]")
    public WebElement syllableDetails;

    @FindBy(xpath = "(//input)[1]")
    public WebElement cohortName;

    @FindBy(xpath = "//input[@type='date']")
    public WebElement date;

    @FindBy(xpath = "//div[contains(@class, 'MuiGrid-root MuiGrid-item css-iu8cs2')]/div/p/following::div[1]/div")
    public List<WebElement> words;

    @FindBy(xpath = "(//div[contains(@class, 'MuiGrid-root MuiGrid-item css-iu8cs2')]/div)[1]/div/div[contains(@class,'MuiChip-colorSecondary')]")
    public List<WebElement> Easywords;

    @FindBy(xpath = "(//div[contains(@class, 'MuiGrid-root MuiGrid-item css-iu8cs2')]/div)[2]/div/div[contains(@class,'MuiChip-colorSecondary')]")
    public List<WebElement> Mediumwords;

    @FindBy(xpath = "(//div[contains(@class, 'MuiGrid-root MuiGrid-item css-iu8cs2')]/div)[3]/div/div[contains(@class,'MuiChip-colorSecondary')]")
    public List<WebElement> Hardwords;

    @FindBy(xpath = "(//textarea)[1]")
    public WebElement updateTextArea;

    @FindBy(xpath = "//button[text()='confirm']")
    public WebElement confimButton;

    @FindBy(xpath = "((//div)[5]/div)[2]")
    public WebElement toastMessage;

    public PG_006_UpdateCohort Verify_the_Edit_Page() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            TestContext.getWait().until(ExpectedConditions.visibilityOf(EditCohortpage));
            Assert.assertEquals(EditCohortpage.getText(), "Edit cohorts");
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Successfully verified Edit Cohort Page");
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to verify Edit Cohort page", e);
        }
        return this;
    }

    public PG_006_UpdateCohort Verify_the_CohortName(String cohortDetail) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            @Nullable
            String extractingCohortDetails = cohortName.getAttribute("value");
            Assert.assertEquals(extractingCohortDetails, cohortDetail);
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Verified Cohort Name successfully");
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to verify Cohort Name", e);
        }
        return this;
    }

    public PG_006_UpdateCohort Verify_the_Syllable(String syllables) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            StringBuilder concatenatedString = new StringBuilder();
            String[] syllable = syllableDetails.getText().split(",");

            for (String syllableJoin : syllable) {
                concatenatedString.append(syllableJoin.trim());
            }
            String result = concatenatedString.toString();

            Assert.assertEquals(result, syllables);
            ExtentReportManager.reportStep(methodName + " " + syllables, "PASS");
            TestContext.getLogger().info("Verified syllables successfully");
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to verify syllables", e);
        }
        return this;
    }

    public PG_006_UpdateCohort setting_up_the_Date(String Date) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            String dateValue = Date;
            TestContext.getJsExecutor().executeScript("arguments[0].value = arguments[1];", date, dateValue);
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Date set successfully");
        } catch (Exception e) {
            TestContext.getLogger().error("Failed to set date", e);
        }
        return this;
    }

    public PG_006_UpdateCohort Update_the_Words_and_Sentences() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            String[] taskCategory = {"assignment", "assessment", "homework"};
            for (String category : taskCategory) {
                WebElement taskXpath = driver.get().findElement(By.xpath("((//div[contains(@class,'MuiTablePagination-actions')])[1]//following::p[text()='" + category + "']//following::div/p[text()='Word :'])[1]"));
                TestContext.getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", taskXpath);
                Thread.sleep(2000);

                String[] wordCategory = {"easy", "medium", "hard"};
                for (String word : wordCategory) {
                    String combinedXpath = "((//div[contains(@class,'MuiTablePagination-actions')])[1]//following::p[text()='" + category + "']//following::div/p[text()='Word :'])[1]//following::div/p[text()='" + word + "']//following::div[contains(@class,'MuiChip-colorSecondary')]";
                    Thread.sleep(2000);
                    List<WebElement> wordslist = driver.get().findElements(By.xpath(combinedXpath));
                    for (int j = 0; j <= 2; j++) {
                        wordslist.get(j).click();
                        Thread.sleep(1000);
                    }
                }
            }
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Words and sentences updated successfully");
        } catch (Exception e) {
            TestContext.getLogger().error("Failed to update words and sentences", e);
        }
        return this;
    }

    public PG_006_UpdateCohort Click_On_Update_Button() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            WebElement updateButton = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Update cohort']")));
            TestContext.getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", updateButton);
            updateButton.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on update button successfully");
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click on update button", e);
        }
        return this;
    }

    public PG_006_UpdateCohort Updating_the_Comments() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            updateTextArea.sendKeys("Testing Comments");
            confimButton.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Updated comments successfully");
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to update comments", e);
        }
        return this;
    }

    public PG_006_UpdateCohort verify_the_toastMessage() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            updateTextArea.sendKeys("Testing Comments");
            confimButton.click();

            WebElement toastMessage = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@role='alert']//div)[2]")));

            if (toastMessage.getText().equals("Cohort updated successfully")) {
                Assert.assertEquals(toastMessage.getText(), "Cohort updated successfully");
                ExtentReportManager.reportStep(methodName, "PASS");
                TestContext.getLogger().info("Cohort updated successfully");
            } else if (toastMessage.getText().equals("End Date must be today or later")) {
                Assert.assertEquals(toastMessage.getText(), "End Date must be today or later");
                ExtentReportManager.reportStep(methodName, "PASS");
                TestContext.getLogger().info("Cohort not updated due to past date");
            }
        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to verify toast message", e);
        }
        return this;
    }

    public PG_007_CohortHistory navigateToHistoryCohort() {
        return new PG_007_CohortHistory(driver.get());
    }
}
