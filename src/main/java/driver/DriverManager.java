package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import lombok.SneakyThrows;
import utilities.Data;

public abstract class DriverManager {
	protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

	protected abstract void createDriver(ITestContext context, ITestResult result);

	public void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

	@SneakyThrows
	public static String getGridURL(ITestContext context) {
		final Data data = Data.get(context.getCurrentXmlTest().getParameter("data-file"));
		return data.getGridURL();
	}

	@SneakyThrows
	public String getSessionId(ITestContext context, ITestResult result) {
		return ((RemoteWebDriver) getDriver(context, result)).getSessionId().toString();
	}

	@SneakyThrows
	public WebDriver getDriver(ITestContext context, ITestResult result) {
		if (driver.get() == null) {
			createDriver(context, result);
		}
		return driver.get();
	}
}
