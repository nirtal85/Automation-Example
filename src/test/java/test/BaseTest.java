package test;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import driver.DriverManager;
import driver.DriverManagerFactory;
import driver.DriverType;
import utils.AllureAttachment;
import utils.Data;

public class BaseTest {
	public WebDriver driver;
	DriverManager driverManager;
	public Data data;
	String sessionId;
	AllureAttachment allureAttachment;

	@Parameters({ "data-file" })
	@BeforeClass
	public void beforeClass(String dataFile) throws Exception {
		data = Data.get(dataFile);
	}

	@Parameters({ "baseUrl" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String baseUrl, ITestContext context) throws Exception {
		driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
		driver = driverManager.getDriver(context);
		driver.navigate().to(baseUrl);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult testResult, ITestContext context) {
		if ("true".equals(System.getProperty("enableVideo"))) {
			allureAttachment = new AllureAttachment();
			sessionId = allureAttachment.getSessionId(testResult, context);
		}
		
		driverManager.quitDriver();
		if ("true".equals(System.getProperty("enableVideo"))) {
			allureAttachment.attachAllureVideo(sessionId, context);
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

}
