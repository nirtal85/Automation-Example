package driver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import utils.Data;

public class OperaDriverManager extends DriverManager {
	public Data data;

	@Override
	protected void createDriver(ITestContext context) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("opera");
		capabilities.setVersion("55.0");
		capabilities.setCapability("enableVNC", true);
		if ("true".equals(System.getProperty("enableVideo"))) {
			capabilities.setCapability("enableVideo", true);
		}
		driver = new RemoteWebDriver(URI.create(data.getGridURL()+"/wd/hub").toURL(), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

}
