package test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.google.inject.Inject;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import pages.LoginPage;
import utils.BaseListener;

@Guice
@Listeners({ BaseListener.class, VideoListener.class })
public class Selenide extends BaseTest {
	@Inject
	LoginPage loginPage;

	@Epic("Allure examples")
	@Feature("Testng support")
	@Severity(SeverityLevel.CRITICAL)
	@Video
	@Test(description = "1", groups = { "sanity" })
	public void a() throws Exception {
		open("");
		testEasy1();
		testEasy2();
		assertThat(title()).isEqualTo("nir");
	}

	@Epic("Allure examples")
	@Feature("Testng support")
	@Severity(SeverityLevel.CRITICAL)
	@Video
	@Test(description = "1", groups = { "sanity" })
	public void b() throws Exception {
		open("");
		testEasy1();
		testEasy2();
		assertThat(title()).isEqualTo("nir");
	}

	@Step
	public void testEasy1() {
		System.out.println(1 + 1);
	}

	@Step
	public void testEasy2() {
		System.out.println(1 + 1);
	}
}