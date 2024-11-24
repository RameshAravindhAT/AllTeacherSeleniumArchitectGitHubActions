package testPackage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import projectSpecifications.BaseClass;
import utils.ExtentReportManager;
import utils.TestContext;


@Listeners(utils.CustomTestListener.class)
public class TC_006_UpdateCohort extends BaseClass{

		
		@BeforeClass
		public void testDetails()
		{
			TestContext.setSheetName("UpdateCohort"); 
		}
	
	
	@Test(dataProvider = "sendData")
	public void validateUpdateCohorts(String testNameDetails,String authorName, String category,String username, String password, String message,String cohortSelection, String date,String CohortDetails,String syllableDetails)
	{
		  ExtentReportManager.setTest(extent.createTest(testNameDetails)); // Create the test instance in Extent Reports
	      ExtentReportManager.getTest().assignAuthor(authorName); // Assign the author for the test
	      ExtentReportManager.getTest().assignCategory(category);  // Assign the category for the test

	     TestContext.getLoginPage()
        .Enter_the_username(username)
        .Enter_the_password(password)
        .Click_on_the_loginButton()
        .VerifyToastMessage(message)
        .click_On_Cohortmodule()
        .Click_on_Edit_Button(cohortSelection)
        .Verify_the_Edit_Page()
        .Verify_the_CohortName(CohortDetails)
        .Verify_the_Syllable(syllableDetails)
        .setting_up_the_Date(date)
        .Update_the_Words_and_Sentences()
        .Click_On_Update_Button()
        .Updating_the_Comments()
        .verify_the_toastMessage();
	}
	
	}

