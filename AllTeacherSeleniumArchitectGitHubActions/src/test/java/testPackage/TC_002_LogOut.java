package testPackage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import projectSpecifications.BaseClass;
import utils.ExtentReportManager;
import utils.TestContext;


@Listeners(utils.CustomTestListener.class)
public class TC_002_LogOut extends BaseClass{
	
	
	
	@BeforeClass
	public void testDetails()
	{
		 TestContext.setSheetName("Logout");
	}
	
	
	@Test(dataProvider = "sendData")
    public void validateLogOut(String testNameDetails,String authorName, String category,String username, String password, String message, String promptActions, String expectedURL) throws InterruptedException {
		
		   ExtentReportManager.setTest(extent.createTest(testNameDetails)); // Create the test instance in Extent Reports
	        ExtentReportManager.getTest().assignAuthor(authorName); // Assign the author for the test
	        ExtentReportManager.getTest().assignCategory(category);  // Assign the category for the test

	        // Perform the login action using the login page object
	        TestContext.getLoginPage()
            .Enter_the_username(username)
            .Enter_the_password(password)
            .Click_on_the_loginButton()
            .VerifyToastMessage(message)
		    .click_On_LogOut_Button()
		    .Click_On_Prompt(promptActions)
		    .verify_the_Results(expectedURL);
    }

}


