package driver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import lombok.SneakyThrows;

public class ChromeDriverManager extends DriverManager {

	@Override
	@SneakyThrows
	protected void createDriver(ITestContext context, ITestResult result) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName("chrome");
		capabilities.setVersion("72.0");
		capabilities.setCapability("screenResolution", "1280x1024x24");
		capabilities.setCapability("name", result.getMethod().getMethodName());
		capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
		capabilities.setCapability("enableVNC", true);
		if ("true".equals(System.getProperty("enableVideo"))) {
			capabilities.setCapability("enableVideo", true);
			capabilities.setCapability("videoFrameRate", 24);
		}
		driver.set(new RemoteWebDriver(URI.create(getGridURL(context) + "/wd/hub").toURL(), capabilities));
	}

	public static ChromeOptions getChromeOptions() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("disable-infobars");
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		chromeOptions.setExperimentalOption("prefs", prefs);
		return chromeOptions;
	}
}
