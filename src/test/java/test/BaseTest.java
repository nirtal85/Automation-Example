package test;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
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

	@Parameters({ "data-file" })
	@BeforeSuite
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
		AllureAttachment allureAttachment = new AllureAttachment();
		String sessionId = allureAttachment.getSessionId(testResult);
		driverManager.quitDriver();
		AllureAttachment.attachAllureVideo(sessionId);
	}

	public WebDriver getDriver() {
		return driver;
	}

}
