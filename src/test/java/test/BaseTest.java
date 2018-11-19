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
import pages.LoginPage;
import utils.AllureAttachment;
import utils.Data;

public class BaseTest {
	public WebDriver driver;
	public Data data;
	public LoginPage loginPage;
	DriverManager driverManager;

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
		loginPage = new LoginPage(driver);
		driver.navigate().to(baseUrl);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult testResult, ITestContext context) {
		if ("true".equals(System.getProperty("enableVideo"))) {
			AllureAttachment allureAttachment = new AllureAttachment();
			String sessionId = allureAttachment.getSessionId(testResult, context);
			driverManager.quitDriver();
			allureAttachment.attachAllureVideo(sessionId, context);
		} else {
			driverManager.quitDriver();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}
}
