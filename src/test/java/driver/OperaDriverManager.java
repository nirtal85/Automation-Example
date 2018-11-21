package driver;

import java.net.MalformedURLException;
import java.net.URI;
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
			capabilities.setCapability("videoFrameRate", 24);
		}
		driver = new RemoteWebDriver(URI.create(data.getGridURL() + "/wd/hub").toURL(), capabilities);
	}
}
