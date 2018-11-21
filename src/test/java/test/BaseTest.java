package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
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

	@Parameters({ "baseUrl", "driverType" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String baseUrl, @Optional("CHROME") DriverType driverType, ITestContext context) throws Exception {
		driverManager = DriverManagerFactory.getManager(driverType);
		driver = driverManager.getDriver(context);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
