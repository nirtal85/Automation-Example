package test;

import org.openqa.selenium.WebDriver;
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

	@Parameters({ "data-file" })
	@BeforeClass
	public void beforeSuite(String dataFile) throws Exception {
		data = Data.get(dataFile);
	}

	@Parameters({ "baseUrl" })
	@BeforeMethod(alwaysRun = true)
	public void beforeClass(String baseUrl) throws Exception {
		driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
		driver = driverManager.getDriver();
		driver.navigate().to(baseUrl);
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp(ITestResult testResult) {
		if ("true".equals(System.getProperty("enableVideo"))) {
			AllureAttachment allureAttachment = new AllureAttachment();
			sessionId = allureAttachment.getSessionId(testResult);
		}

		driverManager.quitDriver();
		if ("true".equals(System.getProperty("enableVideo"))) {
			AllureAttachment.attachAllureVideo(sessionId);
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

}
