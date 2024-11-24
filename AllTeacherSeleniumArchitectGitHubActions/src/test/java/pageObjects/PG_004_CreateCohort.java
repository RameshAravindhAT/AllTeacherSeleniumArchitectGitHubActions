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

public class PG_004_CreateCohort extends BaseClass {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public PG_004_CreateCohort(WebDriver driver) {
        this.driver.set(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//p[text()='Create cohort']/following::button/following::div)[3]/button[text()]")
    public List<WebElement> syllableCharacters;

    @FindBy(xpath = "(//input[@type='text'])[1]")
    public WebElement cohortName;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement Confirm;

    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement Cancel;

    @FindBy(xpath = "(//div[@role='alert']//div)[2]")
    public WebElement toastMessage;

    @FindBy(xpath = "//p[text()='Eligible students']")
    public WebElement EligibleStudents;

    @FindBy(xpath = "(//input[@type='text'])[1]")
    public WebElement inputareaforCohortName;
    
    @FindBy(xpath="//label[text()='Description']/following::div/input")
    public WebElement descriptionName;
    
    @FindBy(xpath = "(//p[text()='Eligible students']/following::div)[4]/p[1]")
    public WebElement CohortDetails;

    @FindBy(xpath = "(//p[text()='Eligible students']/following::div)[4]/p[3]")
    public WebElement SyllableDetails;

    @FindBy(xpath = "//button[@id='create-and-go-next-button']")
    public WebElement Createandgonext;

    @FindBy(xpath = "//button[@id='cancel-button']")
    public WebElement cancel;

    @FindBy(xpath = "//span[text()='Add words']")
    public WebElement addWords;

    @FindBy(xpath = "//span[text()='Add sentences']")
    public WebElement addScentence;

    @FindBy(xpath = "//p[text()=' Click to add ']/following::div[1]/div[1]//div[contains(@class, 'MuiChip-colorPrimary') and contains(@class, 'css-17197bz')]")
    public List<WebElement> Easywords;

    @FindBy(xpath = "//p[text()=' Click to add ']/following::div[1]/div[2]//div[contains(@class, 'MuiChip-colorPrimary') and contains(@class, 'css-17197bz')]")
    public List<WebElement> Mediumwords;

    @FindBy(xpath = "//p[text()=' Click to add ']/following::div[1]/div[3]//div[contains(@class, 'MuiChip-colorPrimary') and contains(@class, 'css-17197bz')]")
    public List<WebElement> Hardwords;

    @FindBy(xpath = "//div[contains(@class, 'MuiGrid-root') and contains(@class, 'MuiGrid-item') and contains(@class, 'css-1d81pem')]/button")
    public WebElement confirmButton;

    @FindBy(xpath = "//p[text()='My cohorts']")
    public WebElement MyCohort;

    @FindBy(xpath = "//div[contains(@class,'MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 MuiCard-root css-3ozxh9')]/p[1]")
    public List<WebElement> verifyCreatedCohortName;

    public PG_004_CreateCohort choose_the_syllable(String syllable) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            char[] charArray = syllable.toCharArray();

            for (int i = 0; i < charArray.length; i++) {
                char character = charArray[i];
                driver.get().findElement(By.xpath("(//p[text()='Create cohort']/following::button/following::div)[3]/button[text()='" + character + "']")).click();
                Thread.sleep(1000);  // Consider replacing this with WebDriverTestContext.getwait().
            }

            if (syllable.length() < 5) {
                Confirm.click();
                String cohortNamellimitWarnings = "Please select at least 5 syllables";
                String actualToastMessage = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@role='alert']//div)[2]"))).getText();
                TestContext.getLogger().info(actualToastMessage);

                if (actualToastMessage.equals(cohortNamellimitWarnings)) {
                    ExtentReportManager.reportStep(methodName + " " + syllable + " " + actualToastMessage, "PASS");
                    TestContext.getLogger().info("Chosen the syllable");
                }
            } else if (syllable.length() > 5) {
                String syllablelengthWarnings = "Please enter a cohort name (20 char limit)";
                inputareaforCohortName.sendKeys("");
                Confirm.click();
                Thread.sleep(1000);  // Consider replacing this with WebDriverTestContext.getwait().
                String actualToastMessage = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@role='alert']//div)[2]"))).getText();

                if (actualToastMessage.equals(syllablelengthWarnings)) {
                    ExtentReportManager.reportStep(methodName + " " + syllable + " " + actualToastMessage, "PASS");
                    TestContext.getLogger().info(syllablelengthWarnings);
                }
            }
        } catch (Exception e) {
            TestContext.getLogger().error("Error in choosing syllable", e);
            ExtentReportManager.reportStep(methodName + " Error: " + e.getMessage(), "FAIL");
        }
        return this;
    }

    public PG_004_CreateCohort enter_the_cohortName(String Cohortname, String Descriptions,String languages) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='text'])[1]")));
            if(languages.equalsIgnoreCase("English"))
            {
            	   cohortName.sendKeys(Cohortname);
            }
            else
            {
            	  cohortName.sendKeys(Cohortname);
            	  Thread.sleep(2000);
            	  descriptionName.sendKeys(Cohortname);
            }
         
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Entered the Cohort Name");
        } catch (Exception e) {
            TestContext.getLogger().error("Failed to enter cohort name", e);
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
        }
        return this;
    }

    public PG_004_CreateCohort Click_On_ConfirmButton() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            Confirm.click();
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Confirm button has been clicked");
        } catch (Exception e) {
            TestContext.getLogger().error("Error clicking Confirm button", e);
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
        }
        return this;
    }

    public PG_004_CreateCohort verifying_the_toast(String expectedMessage) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            WebElement toastContent = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@role='alert']//div)[2]")));
            String actualToastMessage = toastContent.getText();

            if (actualToastMessage.equals("Cohort created successfully")) {
                ExtentReportManager.reportStep(methodName + " " + actualToastMessage, "PASS");
                TestContext.getLogger().info("Toast has been verified");
            } else if (actualToastMessage.equals(expectedMessage)) {
                ExtentReportManager.reportStep(methodName + " " + actualToastMessage, "PASS");
            }
        } catch (Exception e) {
            TestContext.getLogger().error("Error verifying toast", e);
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
        }
        return this;
    }

    public PG_004_CreateCohort verify_EligibleStudentspage() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            TestContext.getWait().until(ExpectedConditions.visibilityOf(EligibleStudents));
            Assert.assertEquals(EligibleStudents.getText(), "Eligible students");
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Verified the Eligible Students page");
        } catch (Exception e) {
            TestContext.getLogger().error("Error verifying Eligible Students page", e);
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
        }
        return this;
    }

    public PG_004_CreateCohort Click_On_the_EliglibleStudents() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            List<WebElement> eligibleStudentlist = driver.get().findElements(By.xpath("//input[@type='radio']"));
            int studentsCount = eligibleStudentlist.size();
            int count = 0;
            for (WebElement radioElement : eligibleStudentlist) {
                if (radioElement.isSelected()) {
                    count++;
                }
            }

            if (count == studentsCount) {
                ExtentReportManager.reportStep(methodName + " All radio buttons for students are already selected.", "PASS");
                TestContext.getLogger().info("All radio buttons for students are already selected.");
            } else {
                for (WebElement radioElement : eligibleStudentlist) {
                    if (!radioElement.isSelected()) {
                        radioElement.click();
                        WebElement dummyClickDiv = driver.get().findElement(By.xpath("(//input[@type='radio']/following::div)[1]"));
                        dummyClickDiv.click();
                    }
                }
            }
        } catch (Exception e) {
            TestContext.getLogger().error("Error clicking eligible students", e);
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
        }
        return this;
    }

    public PG_004_CreateCohort Click_On_Create_and_go_Next() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            TestContext.getWait().until(ExpectedConditions.elementToBeClickable(Createandgonext));
            Createandgonext.click();
            TestContext.getLogger().info("Button is clicked");
            ExtentReportManager.reportStep(methodName, "PASS");
        } catch (Exception e) {
            TestContext.getLogger().error("Error clicking Create and Go Next button", e);
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
        }
        return this;
    }
    public PG_004_CreateCohort Click_On_AddWords() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            for (int attempt = 1; attempt <= 4; attempt++) {
                // Wait for the Add Words button to be clickable and click it
                TestContext.getWait().until(ExpectedConditions.elementToBeClickable(addWords)).click();
                Thread.sleep(2000);
                TestContext.getLogger().info("Add word button is clicked");

                int Easycount = Easywords.size();
                int Mediumcount = Mediumwords.size();
                int Hardcount = Hardwords.size();
                int[] counts = {Easycount, Mediumcount, Hardcount};
                
                // Check if any category has 5 words and confirm
                if (Easycount == 5 || Mediumcount == 5 || Hardcount == 5) {
                	 TestContext.getWait().until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
                    Thread.sleep(3000);
                    TestContext.getLogger().info("Check for each field has count of 5");
                }
                
                // Iterate through each difficulty level to click remaining words
                for (int i = 0; i < counts.length; i++) {
                    if (counts[i] < 5) {
                        int RemainingelementCounttobeClick = 5 - counts[i];
                        TestContext.getLogger().info("Remaining to click for difficulty " + (i + 1) + ": " + RemainingelementCounttobeClick);

                        int divIndex = i + 1;
                        List<WebElement> unSelectWords = driver.get().findElements(
                            By.xpath("//p[text()=' Click to add ']/following::div[1]/div[" + divIndex + "]//div[contains(@class, 'MuiChip-colorSecondary') and contains(@class, 'css-11wbon5')]")
                        );

                        // If no unselected words, click "more..." button
                        if (unSelectWords.isEmpty()) {
                            WebElement moreButton = driver.get().findElement(By.xpath("(//span[text()='more...'])["+attempt+"]"));
                            if (moreButton.isDisplayed()) {
                                moreButton.click();
                                Thread.sleep(5000);
                                TestContext.getLogger().info("Clicked on 'more...' button to load more words");
                            }
                        } else {
                            // Click the remaining words based on the count
                            int wordsToClick = Math.min(RemainingelementCounttobeClick, unSelectWords.size());
                            for (int k = 0; k < wordsToClick; k++) {
                                Thread.sleep(1000);
                                unSelectWords.get(k).click();
                            }
                        }
                    }
                }
            }

            // Report success and log
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info("Add words action completed successfully");

        } catch (Exception e) {
            // Report failure and log the exception
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to click on Add Words or process the word categories", e);
        }

        return this;
    }
    
    public PG_004_CreateCohort Verify_the_Created_Cohort(String cohortName) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Click on the confirm button
        	 TestContext.getWait().until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
            Thread.sleep(3000);
            
            // Assert that "My cohorts" is displayed
            Assert.assertEquals(MyCohort.getText(), "My cohorts");

            // Convert the cohort name to uppercase for comparison
            String cohortNameExpected = cohortName.toUpperCase();

            // Iterate over the list of created cohort names and check if the expected cohort name exists
            for (int i = 0; i < verifyCreatedCohortName.size(); i++) {
                String actualCohortName = verifyCreatedCohortName.get(i).getText();

                // If the cohort name matches the expected name, log syllables
                if (actualCohortName.equalsIgnoreCase(cohortNameExpected)) {
                    Assert.assertEquals(actualCohortName, cohortNameExpected);
                    
                    // Get the syllables associated with the cohort
                    List<WebElement> syllables = driver.get().findElements(By.xpath("//div[contains(@class,'MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 MuiCard-root css-3ozxh9')]/p[" + (i + 1) + "]"));
                    for (WebElement syllable : syllables) {
                        TestContext.getLogger().info(syllable.getText());
                    }
                }
            }

            // Report step as PASS if the cohort is verified successfully
            ExtentReportManager.reportStep(methodName, "PASS");
            TestContext.getLogger().info(methodName + " verified successfully for cohort: " + cohortName);

        } catch (Exception e) {
            // Report failure in case of an exception
            ExtentReportManager.reportStep(methodName, "FAIL: " + e.getMessage());
            TestContext.getLogger().error("Failed to verify the created cohort: " + cohortName, e);
        }

        return this;
    }


}
