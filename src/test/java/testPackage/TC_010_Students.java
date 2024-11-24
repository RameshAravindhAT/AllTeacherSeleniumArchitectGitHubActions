package testPackage;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import projectSpecifications.BaseClass;
import utils.ExtentReportManager;
import utils.TestContext;

@Listeners(utils.CustomTestListener.class)
public class TC_010_Students extends BaseClass{
	

	
	@BeforeClass
	public void testDetails()
	{
		TestContext.setSheetName("Students"); 

	}


@Test(dataProvider = "sendData")
public void validateStudentsRecord(String testNameDetails,String authorName, String category,String username, String password, String message,String classDetails, String studentId, String expectedStudentId)
{
	ExtentReportManager.setTest(extent.createTest(testNameDetails)); // Create the test instance in Extent Reports
    ExtentReportManager.getTest().assignAuthor(authorName); // Assign the author for the test
    ExtentReportManager.getTest().assignCategory(category);  // Assign the category for the test

   TestContext.getLoginPage()
    .Enter_the_username(username)
    .Enter_the_password(password)
    .Click_on_the_loginButton()
    .VerifyToastMessage(message)
    .click_On_StudentsModule()
    .Verify_the_Students_Page()
    .Click_on_the_classDetail(classDetails)
    .Click_On_the_student(studentId)
    .Verify_the_StudentDetail(expectedStudentId);
}

}

