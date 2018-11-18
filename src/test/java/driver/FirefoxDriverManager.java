package driver;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.Data;

public class FirefoxDriverManager extends DriverManager {
	public Data data;

	@Override
	protected void createDriver(ITestContext context) throws JsonParseException, JsonMappingException, IOException {
		data = Data.get(context.getCurrentXmlTest().getParameter("data-file"));
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("firefox");
		capabilities.setVersion("63.0");
		capabilities.setCapability("enableVNC", true);
		if ("true".equals(System.getProperty("enableVideo"))) {
			capabilities.setCapability("enableVideo", true);
		}
		driver = new RemoteWebDriver(URI.create(data.getGridURL()+"/wd/hub").toURL(), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

}
