package ui;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableMap;

import pages.LoginPage;

import driver.DriverManager;
import driver.DriverManagerFactory;
import driver.DriverType;
import utilities.Data;

import static utilities.AllureAttachment.deleteVideos;
import static utilities.AllureAttachment.attachVideo;

public class BaseTest {
	private static final String ENABLE_VIDEO = System.getProperty("enableVideo");
	public WebDriver driver;
	public Data data;
	public LoginPage loginPage;
	DriverManager driverManager;

	@Parameters({ "baseUrl" })
	@BeforeSuite
	void setAllureEnvironment(String baseUrl) {
		allureEnvironmentWriter(ImmutableMap.<String, String>builder().put("Browser", "Chrome")
				.put("Browser.Version", "72.0").put("URL", baseUrl).build());
	}

	@Parameters({ "data-file" })
	@BeforeClass
	public void beforeClass(String dataFile) throws Exception {
		data = Data.get(dataFile);
	}

	@Parameters({ "baseUrl", "driverType" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String baseUrl, @Optional("CHROME") DriverType driverType, ITestContext context,
			ITestResult result) throws Exception {
		driverManager = DriverManagerFactory.getManager(driverType);
		driver = driverManager.getDriver(context, result);
		context.setAttribute("WebDriver", this.driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		loginPage = new LoginPage(driver);
		driver.navigate().to(baseUrl);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult testResult, ITestContext context)
			throws JsonParseException, JsonMappingException, IOException {
		if (ENABLE_VIDEO.equals("true")) {
			String sessionId = driverManager.getSessionId(context, testResult);
			driverManager.quitDriver();
			attachVideo(sessionId, context);
		} else {
			driverManager.quitDriver();
		}
	}

	@AfterSuite
	public void afterSuite(ITestContext context) throws JsonParseException, JsonMappingException, IOException {
		if (ENABLE_VIDEO.equals("true")) {
			deleteVideos(context);
		}
	}
}
