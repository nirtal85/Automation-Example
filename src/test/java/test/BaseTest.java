package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import driver.DriverManager;
import driver.DriverManagerFactory;
import driver.DriverType;
import utils.Data;

public class BaseTest {
	public WebDriver driver;
    DriverManager driverManager;

	public Data data;

	@Parameters({ "baseUrl", "data-file" })
	@BeforeClass(alwaysRun = true)
	public void beforeClass(String baseUrl, String dataFile) throws Exception {
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
        driver = driverManager.getDriver();
        driver.navigate().to(baseUrl);
		data = Data.get(dataFile);

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
        driverManager.quitDriver();
	}

	public WebDriver getDriver() {
		return driver;
	}
}
