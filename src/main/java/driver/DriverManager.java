package driver;

import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utilities.Data;

public abstract class DriverManager {
	protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

	protected abstract void createDriver(ITestContext context, ITestResult result)
			throws JsonParseException, JsonMappingException, IOException;

	public void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
	
	public byte[] captureScreenshotAsBytes(ITestContext context, ITestResult result)
			throws JsonParseException, JsonMappingException, IOException {
		return ((TakesScreenshot) getDriver(context, result)).getScreenshotAs(OutputType.BYTES);
	}

	public static String getGridURL(ITestContext context) throws JsonParseException, JsonMappingException, IOException {
		final Data data = Data.get(context.getCurrentXmlTest().getParameter("data-file"));
		return data.getGridURL();
	}

	public String getSessionId(ITestContext context, ITestResult result)
			throws JsonParseException, JsonMappingException, IOException {
		return ((RemoteWebDriver) getDriver(context, result)).getSessionId().toString();
	}

	public WebDriver getDriver(ITestContext context, ITestResult result)
			throws JsonParseException, JsonMappingException, IOException {
		if (driver.get() == null) {
			createDriver(context, result);
		}
		return driver.get();
	}
}
