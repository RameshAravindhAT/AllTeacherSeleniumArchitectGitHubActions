package testPackage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import projectSpecifications.BaseClass;
import utils.ExtentReportManager;
import utils.TestContext;

@Listeners(utils.CustomTestListener.class)
public class TC_011_ChatBot extends BaseClass{
	
	
	@BeforeClass
	public void testDetails()
	{
		TestContext.setSheetName("ChatBot"); 
	}
	
	
    @Test(dataProvider = "sendData")
	public void validateChatBotAI(String testNameDetails,String authorName, String category,String username, String password)
	{
    	ExtentReportManager.setTest(extent.createTest(testNameDetails)); // Create the test instance in Extent Reports
        ExtentReportManager.getTest().assignAuthor(authorName); // Assign the author for the test
        ExtentReportManager.getTest().assignCategory(category);  // Assign the category for the test

       TestContext.getLoginPage()
    .Enter_the_username(username)
    .Enter_the_password(password)
    .Click_on_the_loginButton()
    .click_on_ChatBot_Menu()
    .enter_the_Prompt();
	}
	

}
