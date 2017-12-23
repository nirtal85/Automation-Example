package testproject.testproject;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.codeborne.selenide.Configuration;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import utils.BaseListener;

@Listeners({ BaseListener.class })
public class AppTest3 {

	@Epic("Allure examples")
	@Feature("Testng support")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void testEasy() {
		assertThat(title()).isEqualTo("nir");
	}

	@BeforeClass
	@Description("start")
	public void beforeTest() {
		Configuration.browser = "chrome";
		open("http://demo.guru99.com/selenium/guru99home/");
	}
}