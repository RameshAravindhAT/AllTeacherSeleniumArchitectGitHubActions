package testPackage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import projectSpecifications.BaseClass;
import utils.ExtentReportManager;
import utils.TestContext;

@Listeners(utils.CustomTestListener.class)
public class TC_004_CreateCohort extends BaseClass {
	
	@BeforeClass
	public void testDetails()
	{
		TestContext.setSheetName("CreateCohort"); 

	}
	
	
	@Test(dataProvider = "sendData")
    public void validateCreateCohort(String testNameDetails,String authorName, String category,String username, String password, String message, String classDetails, String syllable,String expectedtoastMessage, String cohortName,String Descriptions,String languages, String expectedCohortName) throws InterruptedException {
		

		  ExtentReportManager.setTest(extent.createTest(testNameDetails)); // Create the test instance in Extent Reports
	      ExtentReportManager.getTest().assignAuthor(authorName); // Assign the author for the test
	      ExtentReportManager.getTest().assignCategory(category);  // Assign the category for the test

	     TestContext.getLoginPage()
            .Enter_the_username(username)
            .Enter_the_password(password)
            .Click_on_the_loginButton()
            .VerifyToastMessage(message)
            .click_On_ClassDetails(classDetails)
            .click_On_Create_Custom_Cohort()
            .choose_the_syllable(syllable)
            .enter_the_cohortName(cohortName,Descriptions,languages)
            .Click_On_ConfirmButton()
            .verifying_the_toast(expectedtoastMessage)
            .verify_EligibleStudentspage()
            .Click_On_the_EliglibleStudents()
            .Click_On_Create_and_go_Next()
            .Click_On_AddWords()
            .Verify_the_Created_Cohort(expectedCohortName);
    }
	

}
