package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import projectSpecifications.BaseClass;

public class PG_010_Students extends BaseClass{
	
	  private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public PG_010_Students(WebDriver driver) {
		 this.driver.set(driver);
	        PageFactory.initElements(driver, this);
	}
	

	@FindBy(xpath="(//p)[1]")
	public WebElement Studentspage;

	@FindBy(xpath = "(//p)[1]//following::div[3]/div/span[1][text()='Class  I']")
	public WebElement ClassDetails;

	@FindBy(xpath = "//p[contains(text(),'student id : ')]")
	public WebElement studentDetail;
	
	

	public PG_010_Students Verify_the_Students_Page() {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
		try {
			Thread.sleep(3000);
			Assert.assertEquals(Studentspage.getText(),"Students");
			reportStep(methodName, "pass");
			logger.info(methodName+ " " + "verified");
		} catch (Exception e) {
			logger.info(methodName + " " + "Incorrect page ");
			e.printStackTrace();
		}
		return this;
	}
	
	public PG_010_Students Click_on_the_classDetail(String classDetails) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
		try {
			
			if(driver.get().findElement(By.xpath("//div[contains(@class,'MuiChip-colorPrimary')]"))!=null)
					{
				driver.get().findElement(By.xpath("(//p)[1]//following::div[3]/div/span[1][text()='Class  "+classDetails+"']")).click();
					}
			reportStep(methodName+" "+classDetails, "pass");
			logger.info(methodName+ " " + "verified");
		} catch (Exception e) {
			logger.info(methodName + " " + "Incorrect page ");
			e.printStackTrace();
		}
		return this;
	}
	
	public PG_010_Students Click_On_the_student(String studentid) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
		try {
		 driver.get().findElement(By.xpath("//a[text()='"+studentid+"']")).click();
		 Thread.sleep(2000);
			reportStep(methodName+" "+studentid, "pass");
			logger.info(methodName+ " " + "verified");
		} catch (Exception e) {
			logger.info(methodName + " " + "Incorrect page ");
			e.printStackTrace();
		}
		return this;
	}
	
	public PG_010_Students Verify_the_StudentDetail_page() {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
		try {
			Thread.sleep(5000);
			Assert.assertEquals(Studentspage.getText(),"Student detail");
			reportStep(methodName, "pass");
			logger.info(methodName+ " " + "verified");
		} catch (Exception e) {
			logger.info(methodName + " " + "Incorrect page ");
			e.printStackTrace();
		}
		return this;
	}
	

	public PG_010_Students Verify_the_StudentDetail(String expectedstudentid) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
		try {
            String splitStudentid=studentDetail.getText();
            
            String[] split = splitStudentid.split(":");
            
            String studentId = split[1].trim();
            
            System.out.println(studentId);
			
			Assert.assertEquals(studentId,expectedstudentid);
			driver.get().navigate().back();
			reportStep(methodName+" "+expectedstudentid,"pass");
			logger.info(methodName+ " " + "verified");
		} catch (Exception e) {
			logger.info(methodName + " " + "Incorrect page ");
			e.printStackTrace();
		}
		return this;
	}

}
