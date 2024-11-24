package testPackage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import projectSpecifications.BaseClass;
import utils.ExtentReportManager;
import utils.TestContext;

@Listeners(utils.CustomTestListener.class)
public class TC_009_Language extends BaseClass{
	

	
	@BeforeClass
	public void testDetails()
	{
		TestContext.setSheetName("Languages"); 
	}


@Test(dataProvider = "sendData")
public void validatelanguages(String testNameDetails,String authorName, String category,String username, String password, String message,String languages,String classDetails, String expectedlanguages)
{

	ExtentReportManager.setTest(extent.createTest(testNameDetails)); // Create the test instance in Extent Reports
    ExtentReportManager.getTest().assignAuthor(authorName); // Assign the author for the test
    ExtentReportManager.getTest().assignCategory(category);  // Assign the category for the test

   TestContext.getLoginPage()
    .Enter_the_username(username)
    .Enter_the_password(password)
    .Click_on_the_loginButton()
    .VerifyToastMessage(message)
    .click_On_languageModule()
    .Click_on_the_language(languages)
    .Click_on_the_ConfirmButton()
    .Verify_the_toast_Message_and_syllable_verified(classDetails,expectedlanguages);
}

}
