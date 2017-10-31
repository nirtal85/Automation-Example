package testproject.testproject;

import static org.assertj.core.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class AppTest2  {
	WebDriver driver;
	public static final String USERNAME = "nirtal3";
	public static final String AUTOMATE_KEY = "uQPztMmGdrvzqEcMy8br";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

	@Epic("Allure examples")
	@Feature("Testng support")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void testEasy() {
		assertThat(driver.getTitle()).isEqualTo("nir");
	}

	@Parameters("browser")
	@BeforeClass
	@Description("start")
	public void beforeTest(String browser) throws MalformedURLException {
		switch (browser) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "C:\\tools\\selenium\\geckodriver.exe");
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("browser", "Firefox");
			caps.setCapability("browser_version", "57.0 beta");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "10");
			caps.setCapability("resolution", "1920x1200");
			driver = new RemoteWebDriver(new URL(URL), caps);
			break;
		default:
			throw new WebDriverException("no such driver");
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://demo.guru99.com/selenium/guru99home/");
	}

	@AfterMethod
	@Description("stop")
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("errorScreenshots\\" + testResult.getName() + "-" + ".jpg"));
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterTest() {
		driver.quit();
	}
}