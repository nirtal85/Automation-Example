package testproject.testproject;

import static org.assertj.core.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import utils.BrowserStackTestNGTest;

public class AppTest extends BrowserStackTestNGTest {
	WebDriver driver;

	@Epic("Allure examples")
	@Feature("Testng support")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void testEasy() {
		assertThat(driver.getTitle()).isEqualTo("nir1");
	}

	@BeforeClass
	public void beforeTest() {
		driver.get("http://demo.guru99.com/selenium/guru99home/");
	}

}