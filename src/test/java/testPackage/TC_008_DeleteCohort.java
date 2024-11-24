package testPackage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import projectSpecifications.BaseClass;
import utils.ExtentReportManager;
import utils.TestContext;

@Listeners(utils.CustomTestListener.class)
public class TC_008_DeleteCohort extends BaseClass {
	
	
	@BeforeClass
	public void testDetails()
	{
		TestContext.setSheetName("DeleteCohort"); 

	}


@Test(dataProvider = "sendData")
public void validatehistoryOfCohorts(String testNameDetails,String authorName, String category,String username, String password, String message,String cohortSelection, String promptActions, String cohortExists)
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
    .Click_Delete_to_RemoveCohort(cohortSelection)
    .Choose_the_prompts_and_verify_the_toastMessage(promptActions, cohortExists);
}

}

