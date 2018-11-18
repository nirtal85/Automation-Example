package driver;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public abstract class DriverManager {
	protected WebDriver driver;

	protected abstract void createDriver(ITestContext context) throws JsonParseException, JsonMappingException, IOException;

	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public WebDriver getDriver(ITestContext context) throws JsonParseException, JsonMappingException, IOException {
		if (driver == null) {
			createDriver(context);
		}
		return driver;
	}
}
