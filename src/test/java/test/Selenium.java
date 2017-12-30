package test;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class Selenium {
	WebDriver driver;

	@Epic("Allure examples")
	@Feature("Testng support")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void testEasy() {
		assertThat(driver.getTitle()).isEqualTo("nir1");
	}

	@BeforeClass
	public void beforeClass() {

		driver = new FirefoxDriver();
		driver.get("http://demo.guru99.com/selenium/guru99home/");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}