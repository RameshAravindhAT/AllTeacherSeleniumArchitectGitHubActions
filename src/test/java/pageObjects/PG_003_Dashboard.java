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
import utils.ExtentReportManager;
import utils.TestContext;

public class PG_003_Dashboard extends BaseClass {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public PG_003_Dashboard(WebDriver driver) {
        this.driver.set(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//p[text()='Create custom cohort']//following::div")
    public List<WebElement> existingCohortContainer;

    @FindBy(xpath = "//p[text()='Create custom cohort']//following::div/p")
    public List<WebElement> existingCohortDetails;

    @FindBy(xpath = "//div[text()='Cohort']")
    public WebElement cohortModule;

    @FindBy(xpath = "//div[text()='Logout']")
    public WebElement Logout;

    @FindBy(xpath = "(//*[name()='svg'][@class='MuiSvgIcon-root MuiSvgIcon-colorPrimary MuiSvgIcon-fontSizeMedium css-kpzolb'])[1]")
    public WebElement EditButton;

    @FindBy(xpath = "//p[text()='Create custom cohort']")
    public WebElement createCustomCohort;

    @FindBy(xpath = "//div[text()='Language']")
    public WebElement languageModule;

    @FindBy(xpath = "//p[text()='Cohorts available']")
    public WebElement cohortsAvailable;

    @FindBy(xpath = "//div[text()='Students']")
    public WebElement Students;

    // Click on Class Details and verify class name
    public PG_003_Dashboard click_On_ClassDetails(String expectedClass) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            WebElement classDetails = driver.get().findElement(By.xpath("//p[text()='" + expectedClass + "']"));
            classDetails.click();
            String actualClass = classDetails.getText();
            // Use assertions to verify class names match
            Assert.assertEquals(actualClass, expectedClass);
            // Report success step
            ExtentReportManager.reportStep(methodName + " - " + expectedClass, "PASS");
            TestContext.getLogger().info(methodName + " - " + expectedClass);
        } catch (Exception e) {
            // Report failure step
        	ExtentReportManager.reportStep(methodName + " - " + expectedClass, "FAIL: " + e.getMessage());
            TestContext.getLogger().error(methodName + " - " + expectedClass, e);
        }
        return this;
    }

    // Verify Cohort Details and print them
    public PG_003_Dashboard verify_the_CohortDetails() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Scroll to Cohorts Available element using JavaScriptExecutor
        	  TestContext.getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", cohortsAvailable);

            List<WebElement> cohortCard = driver.get().findElements(By.xpath("//p[text()='Existing Cohorts']//following::div[2]/p"));
            
            if (!cohortCard.isEmpty()) {
                // If cohort cards are present, iterate and print cohort details
                for (int c = 1; c < cohortCard.size(); c++) {
                    String cohortCardElement = "(//p[text()='Create custom cohort']//following::div)[" + c + "]";
                    StringBuilder reportText = new StringBuilder();
                    
                    // Wait for elements inside the cohort card to be visible
                    for (int j : new int[]{1, 2}) {
                        WebElement cohortElement = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(cohortCardElement + "//p[" + j + "]")
                        ));
                        String cohortText = cohortElement.getText();
                        reportText.append(cohortText).append("\n");
                    }
                    TestContext.getLogger().info("Cohort details: \n" + reportText.toString());
                }
            } else {
                // If no existing cohorts, handle with fallback XPath
                List<WebElement> cohortCard2 = driver.get().findElements(By.xpath("(//p[text()='Create custom cohort']//following::div)"));
                for (int c = 1; c < cohortCard2.size(); c++) {
                    String cohortCardElement = "(//p[text()='Create custom cohort']//following::div)[" + c + "]";
                    StringBuilder reportText = new StringBuilder();
                    
                    // Wait for elements inside the cohort card to be visible
                    for (int j : new int[]{1, 3}) {
                        WebElement cohortElement = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(cohortCardElement + "//p[" + j + "]")
                        ));
                        String cohortText = cohortElement.getText();
                        reportText.append(cohortText).append("\n");
                    }
                    TestContext.getLogger().info("Cohort details: \n" + reportText.toString());
                }
            }

            // Report success
            ExtentReportManager.reportStep(methodName, "PASS");
        } catch (Exception e) {
            // Report failure
        	ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Error verifying cohort details", e);
        }
        return this;
    }

    // Click on Cohort module
    public PG_005_Cohorts click_On_Cohortmodule() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            cohortModule.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on Cohort Module");
        } catch (Exception e) {
        	ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click on Cohort Module", e);
        }
        return new PG_005_Cohorts(driver.get());
    }

    // Click on Logout button
    public PG_002_LogOut click_On_LogOut_Button() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            Logout.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info(methodName + " - clicked");
        } catch (Exception e) {
        	ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error(methodName + " - not clicked", e);
        }
        return new PG_002_LogOut(driver.get());
    }

    // Click on Create Custom Cohort button
    public PG_004_CreateCohort click_On_Create_Custom_Cohort() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Scroll down to the Create Custom Cohort button
            for (int i = 0; i < 5; i++) {
            	  TestContext.getJsExecutor().executeScript("window.scrollBy(0, window.innerHeight);");
                Thread.sleep(1000); // Optional: Replace with proper WebDriverWait
            }
            createCustomCohort.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on Create Custom Cohort button");
        } catch (Exception e) {
        	ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click on Create Custom Cohort button", e);
        }
        return new PG_004_CreateCohort(driver.get());
    }

    // Click on Language Module
    public PG_009_Languages click_On_languageModule() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            languageModule.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on language module");
        } catch (Exception e) {
        	ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click on language module", e);
        }
        return new PG_009_Languages(driver.get());
    }

    // Click on Students Module
    public PG_010_Students click_On_StudentsModule() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            Students.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Clicked on students module");
        } catch (Exception e) {
        	ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click on students module", e);
        }
        return new PG_010_Students(driver.get());
    }
}
