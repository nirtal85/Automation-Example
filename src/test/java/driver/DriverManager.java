package driver;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import test.BaseTest;
import utils.Data;

public abstract class DriverManager {
	protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	public static Data data;

	protected abstract void createDriver(ITestContext context, ITestResult result)
			throws JsonParseException, JsonMappingException, IOException;

	public void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.set(null);
		}
	}

	public static String getGridURL(ITestContext context) throws JsonParseException, JsonMappingException, IOException {
		data = Data.get(context.getCurrentXmlTest().getParameter("data-file"));
		return data.getGridURL();
	}

	public synchronized String getSessionId(ITestResult testResult, ITestContext context) {
		Object currentClass = testResult.getInstance();
		return ((RemoteWebDriver) ((BaseTest) currentClass).getDriver()).getSessionId().toString();
	}

	public WebDriver getDriver(ITestContext context, ITestResult result)
			throws JsonParseException, JsonMappingException, IOException {
		if (driver.get() == null) {
			createDriver(context, result);
		}
		return driver.get();
	}

}
