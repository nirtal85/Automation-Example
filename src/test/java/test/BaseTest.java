package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import utils.VideoRecord;

public class BaseTest {
	public WebDriver driver;

	@Parameters({ "baseUrl" })
	@BeforeClass(alwaysRun = true)
	public void beforeClass(String baseUrl) throws Exception {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to(baseUrl);
	}

	@Parameters({ "baseUrl" })
	@AfterMethod
	public void cleanUp(String baseUrl) {
		driver.manage().deleteAllCookies();
		if (!driver.getCurrentUrl().equals(baseUrl)) {
			driver.navigate().to(baseUrl);
			driver.navigate().refresh();
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		driver.quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

	@AfterMethod
	public void addVideo() throws Exception {
		VideoRecord.attachment();
	}
}
