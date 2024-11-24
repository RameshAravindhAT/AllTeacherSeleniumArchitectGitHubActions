package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.ExtentTest;

import projectSpecifications.BaseClass;
import utils.ChatBotPrompts;
import utils.ExtentReportManager;
import utils.TestContext;

public class PG_011_ChatBotAI extends BaseClass {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public PG_011_ChatBotAI(WebDriver driver) {
        this.driver.set(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@placeholder='Message chatbot']")
    public WebElement ChatBotInputTextArea;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement promptSubmit;

    int count = 2;

    public PG_011_ChatBotAI enter_the_Prompt() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName().replace("_", " ");
        try {
            // Get the chatbot prompts from the helper class
            ChatBotPrompts chatBot = new ChatBotPrompts(driver.get());
            Object[] prompts = chatBot.getChatBotPrompts(); 

            for (Object prompt : prompts) {
                String promptText = (String) prompt;

                // Clear and type the prompt text into the chat input area
                TestContext.getWait().until(ExpectedConditions.visibilityOf(ChatBotInputTextArea));
                ChatBotInputTextArea.clear();
                ChatBotInputTextArea.sendKeys(promptText);

                // Create an extent report node for this prompt
                ExtentTest node = ExtentReportManager.getTest().createNode(promptText); 

                // Submit the prompt
                promptSubmit.click();
                TestContext.getLogger().info(methodName + " - Prompt submitted: " + promptText);

                // Wait for the chatbot response
                TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'container')]")));

                // Scroll to the bottom to load the latest response
                TestContext.getJsExecutor().executeScript("window.scrollBy(0, document.body.scrollHeight)");

                // Get the last response element
                WebElement lastDiv = TestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//input[@placeholder='Message chatbot']//preceding::div[contains(@class,'container')])[last()]")
                ));

                // Extract the response spans
                List<WebElement> responseSpans = lastDiv.findElements(By.xpath(".//span"));

                // Log the response(s) to the extent report
                if (responseSpans.size() == 1) {
                    String responseText = responseSpans.get(0).getText();
                    node.info(responseText);
                } else {
                    for (WebElement span : responseSpans) {
                        node.info(span.getText());
                    }
                }
            }
            count++;
            if (count == 94) {
                ExtentReportManager.reportStep(methodName, "PASS: All prompts were successfully submitted and responses received.");
                TestContext.getLogger().info(methodName + " - All prompts processed successfully.");
            }

        } catch (Exception e) {
            ExtentReportManager.reportStep(methodName, "FAIL: An error occurred while processing the prompts.");
            TestContext.getLogger().error(methodName + " - Error while submitting prompts or retrieving responses", e);
            e.printStackTrace();
        }
        return this;
    }
}
