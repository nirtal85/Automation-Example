package driver;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	protected WebDriver driver;

	protected abstract void createDriver() throws MalformedURLException;

	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public WebDriver getDriver() throws MalformedURLException {
		if (driver == null) {
			createDriver();
		}
		return driver;
	}
}
