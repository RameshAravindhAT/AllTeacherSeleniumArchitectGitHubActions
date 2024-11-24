package testPackage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import projectSpecifications.BaseClass;
import utils.ExtentReportManager;
import utils.TestContext;

	
@Listeners(utils.CustomTestListener.class)
public class TC_007_HistoryOfCohorts extends BaseClass{
		
		@BeforeClass
		public void testDetails()
		{
			TestContext.setSheetName("HistoryOfCohort"); 
		}
	
	
	@Test(dataProvider = "sendData")
	public void validatehistoryOfCohorts(String testNameDetails,String authorName, String category,String username, String password, String message,String cohortSelection)
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
        .Selection_of_Cohort_to_ViewHistory(cohortSelection)
        .verify_CohortInfo_Page()
        .scroll_to_the_lastUpdate();
	    
	}

}

        
	